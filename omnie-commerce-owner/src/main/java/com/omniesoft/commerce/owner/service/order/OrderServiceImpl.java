package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.common.order.timesheet.OrderPeriod;
import com.omniesoft.commerce.common.order.timesheet.SingleDayTimesheetBuilder;
import com.omniesoft.commerce.common.order.timesheet.TimesheetBuilder;
import com.omniesoft.commerce.owner.controller.order.payload.OrderDetailsDto;
import com.omniesoft.commerce.owner.controller.order.payload.OrderPriceDto;
import com.omniesoft.commerce.owner.controller.order.payload.SaveOrderDto;
import com.omniesoft.commerce.owner.controller.order.payload.SaveOrderSubServices;
import com.omniesoft.commerce.owner.converter.OrderConverter;
import com.omniesoft.commerce.owner.service.organization.DiscountService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.order.OrderStoryRepository;
import com.omniesoft.commerce.persistence.repository.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes.DISCOUNT_VIOLATION_LIMITS;
import static com.omniesoft.commerce.persistence.entity.enums.OrderStatus.*;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderPriceService orderPriceService;
    private final OrderStoryRepository orderStoryRepository;
    private final OrderConverter orderConverter;
    private final DiscountService discountService;
    private final ServiceRepository serviceRepository;
    private final SubServiceRepository subServiceRepository;
    private final SubServicePriceRepository subServicePriceRepository;
    private final UserRepository userRepository;
    private final ServiceTimingRepository serviceTimingRepository;
    private final ServicePriceRepository servicePriceRepository;
    private final ServicePriceConverter servicePriceConverter;
    private final OrderTimesheetService timesheetService;

    @Override
    public Timesheet createTimesheetForAdministrator(UUID serviceId, LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository.findByServiceId(serviceId, start);
        Map<DayOfWeek, List<ScheduleSetting>> schedule = servicePriceConverter.toSchedule(
                servicePriceRepository.findAllByServiceIdAndDay(
                        serviceId,
                        date.getDayOfWeek(),
                        start));

        Timesheet timesheet = timesheetService.buildSingleDaySchedule(serviceId, date, serviceTiming, schedule);

        List<OrderEntity> orders = orderRepository.findReadyToProcessing(
                timesheet.getStart(),
                timesheet.getEnd(),
                serviceId);

        timesheetService.insertAllOrdersWithoutRules(timesheet, orders);
        return timesheet;
    }

    @Override
    public Timesheet createHorizontalTimesheetForAdministrator(UUID serviceId, LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository.findByServiceId(serviceId, start);
        Map<DayOfWeek, List<ScheduleSetting>> schedule = servicePriceConverter.toSchedule(
                servicePriceRepository.findAllByServiceIdAndDay(
                        serviceId,
                        date.getDayOfWeek(),
                        start));

        Timesheet timesheet = timesheetService.buildSingleDaySchedule(serviceId, date, serviceTiming, schedule);

        List<OrderEntity> orders = orderRepository.findAccepted(
                timesheet.getStart(),
                timesheet.getEnd(),
                serviceId);

        timesheetService.insertAllOrders(timesheet, orders);
        return timesheet;
    }

    @Override
    public void editOrder(UUID orderId, SaveOrderDto order, UserEntity admin) {
        LocalDateTime start = LocalDateTime.of(order.getStart().toLocalDate(), LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository.findByServiceId(order.getServiceId(), start);

        Map<DayOfWeek, List<ScheduleSetting>> schedule = servicePriceConverter.toSchedule(
                servicePriceRepository.findAllByServiceIdAndDay(
                        order.getServiceId(),
                        start.getDayOfWeek(),
                        start));

        Timesheet timesheet = timesheetService.buildSingleDaySchedule(order.getServiceId(),
                order.getStart().toLocalDate(),
                serviceTiming,
                schedule);


        List<OrderEntity> orders = orderRepository.findAccepted(
                timesheet.getStart(),
                timesheet.getEnd(),
                order.getServiceId());

        //remove editing order
        orders.removeIf(o -> o.getId().equals(orderId));

        timesheetService.insertAllOrders(timesheet, orders);

        TimesheetBuilder builder = new SingleDayTimesheetBuilder(timesheet);

        OrderPeriod op = builder.orderPeriod(order.getStart(),
                order.getEnd(),
                null,
                null,
                order.getCustomerId(),
                order.getCustomerName(),
                orderConverter.transformSaveOrderSubServices(order.getSubServices())
        );

        if (builder.put(op)) {
            OrderEntity savedOrder = orderRepository.findByIdAndServiceId(orderId, order.getServiceId());
            savedOrder.getSubServices().removeIf(o -> o.getId() != null);

            OrderEntity orderEntity = createOrderEntityWithoutPrices(order, admin, CONFIRM_BY_ADMIN);
            orderEntity.setId(orderId);

            DiscountEntity discountForService = discountService.findMaxServiceDiscount(orderEntity);
            Map<UUID, DiscountEntity> subServicesDiscounts = discountService.findMaxSubServicesDiscounts(orderEntity);
            chooseAndValidateDiscount(orderEntity, discountForService, subServicesDiscounts, serviceTiming);

            orderPriceService.calculatePrice(orderEntity, timesheet);
            orderRepository.save(orderEntity);

        } else throw new UsefulException(OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
    }

    @Override
    public OrderDetailsDto getOrderDetails(UUID serviceId, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        return orderConverter.transformToOrderDetails(orderEntity);
    }

    @Override
    public UUID makeOrder(SaveOrderDto order, UserEntity admin) {

        LocalDateTime start = LocalDateTime.of(order.getStart().toLocalDate(), LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository.findByServiceId(order.getServiceId(), start);

        Map<DayOfWeek, List<ScheduleSetting>> schedule = servicePriceConverter.toSchedule(
                servicePriceRepository.findAllByServiceIdAndDay(
                        order.getServiceId(),
                        start.getDayOfWeek(),
                        start));

        Timesheet timesheet = timesheetService.buildSingleDaySchedule(order.getServiceId(),
                order.getStart().toLocalDate(),
                serviceTiming,
                schedule);


        List<OrderEntity> orders = orderRepository.findAccepted(
                timesheet.getStart(),
                timesheet.getEnd(),
                order.getServiceId());

        timesheetService.insertAllOrders(timesheet, orders);

        TimesheetBuilder builder = new SingleDayTimesheetBuilder(timesheet);

        OrderPeriod op = builder.orderPeriod(order.getStart(),
                order.getEnd(),
                null,
                null,
                order.getCustomerId(),
                order.getCustomerName(),
                orderConverter.transformSaveOrderSubServices(order.getSubServices())
        );

        if (builder.put(op)) {
            OrderEntity orderEntity = createOrderEntityWithoutPrices(order, admin, CONFIRM_BY_ADMIN);

            DiscountEntity discountForService = discountService.findMaxServiceDiscount(orderEntity);
            Map<UUID, DiscountEntity> subServicesDiscounts = discountService.findMaxSubServicesDiscounts(orderEntity);
            chooseAndValidateDiscount(orderEntity, discountForService, subServicesDiscounts, serviceTiming);

            orderPriceService.calculatePrice(orderEntity, timesheet);

            orderRepository.save(orderEntity);
            return orderEntity.getId();

        } else throw new UsefulException(OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
    }

    @Override
    public OrderPriceDto orderPrice(SaveOrderDto order, UserEntity admin) {

        LocalDateTime start = LocalDateTime.of(order.getStart().toLocalDate(), LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository.findByServiceId(order.getServiceId(), start);

        Map<DayOfWeek, List<ScheduleSetting>> schedule = servicePriceConverter.toSchedule(
                servicePriceRepository.findAllByServiceIdAndDay(
                        order.getServiceId(),
                        start.getDayOfWeek(),
                        start));

        Timesheet timesheet = timesheetService.buildSingleDaySchedule(order.getServiceId(),
                order.getStart().toLocalDate(),
                serviceTiming,
                schedule);


        List<OrderEntity> orders = orderRepository.findAccepted(
                timesheet.getStart(),
                timesheet.getEnd(),
                order.getServiceId());

        timesheetService.insertAllOrders(timesheet, orders);

        TimesheetBuilder builder = new SingleDayTimesheetBuilder(timesheet);

        OrderPeriod op = builder.orderPeriod(order.getStart(),
                order.getEnd(),
                null,
                null,
                order.getCustomerId(),
                order.getCustomerName(),
                orderConverter.transformSaveOrderSubServices(order.getSubServices())
        );

        if (builder.put(op)) {
            OrderEntity orderEntity = createOrderEntityWithoutPrices(order, admin, CONFIRM_BY_ADMIN);

            DiscountEntity discountForService = discountService.findMaxServiceDiscount(orderEntity);
            Map<UUID, DiscountEntity> subServicesDiscounts = discountService.findMaxSubServicesDiscounts(orderEntity);
            chooseAndValidateDiscount(orderEntity, discountForService, subServicesDiscounts, serviceTiming);

            orderPriceService.calculatePrice(orderEntity, timesheet);

            return orderConverter.transformEntityToPriceDto(orderEntity);

        } else throw new UsefulException(OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
    }

    private void chooseAndValidateDiscount(OrderEntity order, DiscountEntity discount, Map<UUID, DiscountEntity> subServicesDiscounts, ServiceTimingEntity serviceTiming) {

        if (discount != null && order.getDiscountPercent() < discount.getPercent()) {
            order.setDiscount(discount);
            order.setDiscountPercent(discount.getPercent());
        }

        //validate discount limit
        else {
            if (order.getDiscountPercent() < 0 || order.getDiscountPercent() > serviceTiming.getMaxDiscount()) {
                throw new UsefulException("Order discount " + order.getDiscountPercent() +
                        " > max discount: " + serviceTiming.getMaxDiscount(), DISCOUNT_VIOLATION_LIMITS);
            }
        }
        if (order.getSubServices() != null) {
            for (OrderSubServicesEntity orderSubService : order.getSubServices()) {

                if (subServicesDiscounts.containsKey(orderSubService.getSubService().getId())) {
                    DiscountEntity discountEntity = subServicesDiscounts.get(orderSubService.getSubService().getId());

                    if (orderSubService.getDiscountPercent() < discountEntity.getPercent()) {
                        orderSubService.setDiscount(discountEntity);
                        orderSubService.setDiscountPercent(discountEntity.getPercent());
                    }
                    //validate discount limit
                    else {
                        SubServicePriceEntity subServicePrice = subServicePriceRepository
                                .findBySubServiceIdAndActiveFrom(orderSubService.getSubService().getId(), order.getStart());

                        if (orderSubService.getDiscountPercent() < 0
                                || orderSubService.getDiscountPercent() > subServicePrice.getMaxDiscount()) {
                            throw new UsefulException("OrderSubService discount " +
                                    orderSubService.getDiscountPercent() +
                                    " > max discount: " + subServicePrice.getMaxDiscount(), DISCOUNT_VIOLATION_LIMITS);
                        }

                    }
                }
            }
        }
    }

    private OrderEntity createOrderEntityWithoutPrices(SaveOrderDto order, UserEntity admin, OrderStatus status) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStart(order.getStart());
        orderEntity.setEnd(order.getEnd());
        orderEntity.setDiscountPercent(order.getDiscountPercent());
        orderEntity.setStatus(status);
        orderEntity.setComment(order.getComment());
        orderEntity.setUpdateTime(LocalDateTime.now());

        orderEntity.setService(serviceRepository.findOne(order.getServiceId()));
        orderEntity.setUpdateBy(admin);

        // resolve customer data
        if (order.getCustomerId() != null) {
            UserEntity customer = userRepository.findOne(order.getCustomerId());
            orderEntity.setUser(customer);
            orderEntity.setCustomerName(customer.getProfile().getFullName());
            orderEntity.setCustomerPhone(customer.getProfile().getPhone());
        } else {
            orderEntity.setCustomerName(order.getCustomerName());
            orderEntity.setCustomerPhone(order.getCustomerPhone());
        }

        orderEntity.setSubServices(createOrderSubServiceEntitiesWithoutPrices(order, orderEntity));

        return orderEntity;
    }

    private List<OrderSubServicesEntity> createOrderSubServiceEntitiesWithoutPrices(SaveOrderDto order, OrderEntity
            orderEntity) {

        if (order == null || order.getSubServices() == null || order.getSubServices().isEmpty()) {
            return null;
        }

        List<OrderSubServicesEntity> result = new ArrayList<>();

        for (SaveOrderSubServices orderSubService : order.getSubServices()) {
            OrderSubServicesEntity orderSubServiceEntity = new OrderSubServicesEntity();
            orderSubServiceEntity.setSubService(
                    subServiceRepository.findByIdAndServiceId(orderSubService.getSubServiceId(), order.getServiceId())
            );
            orderSubServiceEntity.setOrder(orderEntity);

            orderSubServiceEntity.setCount(orderSubService.getCount());
            orderSubServiceEntity.setDuration(orderSubService.getDuration());
            orderSubServiceEntity.setDiscountPercent(orderSubService.getDiscountPercent());

            result.add(orderSubServiceEntity);
        }

        return result;

    }


    @Override
    public void confirmOrder(UUID serviceId, UUID orderId, UserEntity admin) {
        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        LocalDateTime start = LocalDateTime.of(orderEntity.getStart().toLocalDate(), LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository.findByServiceId(orderEntity.getService().getId(), start);

        Map<DayOfWeek, List<ScheduleSetting>> schedule = servicePriceConverter.toSchedule(
                servicePriceRepository.findAllByServiceIdAndDay(
                        orderEntity.getService().getId(),
                        start.getDayOfWeek(),
                        start));

        Timesheet timesheet = timesheetService.buildSingleDaySchedule(orderEntity.getService().getId(),
                orderEntity.getStart().toLocalDate(),
                serviceTiming,
                schedule);


        List<OrderEntity> orders = orderRepository.findAccepted(
                timesheet.getStart(),
                timesheet.getEnd(),
                orderEntity.getService().getId());

        timesheetService.insertAllOrders(timesheet, orders);

        TimesheetBuilder builder = new SingleDayTimesheetBuilder(timesheet);

        OrderPeriod op = builder.orderPeriod(orderEntity.getStart(),
                orderEntity.getEnd(),
                null,
                null,
                orderEntity.getUser().getId(),
                orderEntity.getCustomerName(),
                orderConverter.transformOrderSubServices(orderEntity.getSubServices())
        );

        if (builder.put(op)) {
            if (orderEntity.getStatus() == PENDING_FOR_ADMIN) {
                orderEntity.setStatus(CONFIRM_BY_ADMIN);
                orderEntity.setUpdateTime(LocalDateTime.now());
                orderEntity.setUpdateBy(admin);
                orderRepository.save(orderEntity);
            }
            throw new UsefulException("Current status not PENDING_FOR_ADMIN", OwnerModuleErrorCodes.ORDER_STATUS_NOT_CHANGEABLE);

        } else throw new UsefulException(OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
    }

    @Override
    public void cancelOrder(UUID serviceId, UUID orderId, UserEntity admin) {
        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        if (orderEntity.getStatus() != DONE) {
            orderEntity.setStatus(CANCEL_BY_ADMIN);
            orderEntity.setUpdateTime(LocalDateTime.now());
            orderEntity.setUpdateBy(admin);
            orderRepository.save(orderEntity);
        } else throw new UsefulException("Current status DONE", OwnerModuleErrorCodes.ORDER_STATUS_NOT_CHANGEABLE);
    }

    @Override
    public void doneOrder(UUID serviceId, UUID orderId, UserEntity admin) {
        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        if (orderEntity.getStatus() == CONFIRM_BY_USER || orderEntity.getStatus() == CONFIRM_BY_ADMIN) {
            orderEntity.setStatus(DONE);
            orderEntity.setUpdateTime(LocalDateTime.now());
            orderEntity.setDoneBy(admin);
            orderRepository.save(orderEntity);
        } else
            throw new UsefulException("Current status not CONFIRM_BY_USER or CONFIRM_BY_ADMIN", OwnerModuleErrorCodes.ORDER_STATUS_NOT_CHANGEABLE);
    }

    @Override
    public void failOrder(UUID serviceId, UUID orderId, UserEntity admin) {
        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        if (orderEntity.getStatus() == CONFIRM_BY_USER || orderEntity.getStatus() == CONFIRM_BY_ADMIN) {
            orderEntity.setStatus(OrderStatus.FAIL_BY_USER);
            orderEntity.setUpdateTime(LocalDateTime.now());
            orderEntity.setDoneBy(admin);
            orderRepository.save(orderEntity);
        } else
            throw new UsefulException("Current status not CONFIRM_BY_USER or CONFIRM_BY_ADMIN", OwnerModuleErrorCodes.ORDER_STATUS_NOT_CHANGEABLE);
    }
}
