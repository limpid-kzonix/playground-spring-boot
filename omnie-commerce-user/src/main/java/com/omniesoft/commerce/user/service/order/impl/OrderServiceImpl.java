package com.omniesoft.commerce.user.service.order.impl;

import com.omniesoft.commerce.common.component.order.OrderConverter;
import com.omniesoft.commerce.common.component.order.OrderPriceService;
import com.omniesoft.commerce.common.component.order.OrderTimesheetService;
import com.omniesoft.commerce.common.component.order.dto.SaveOrderDto;
import com.omniesoft.commerce.common.component.order.dto.SaveOrderSubServices;
import com.omniesoft.commerce.common.component.order.dto.order.OrderDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderPriceDto;
import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.common.order.timesheet.OrderPeriod;
import com.omniesoft.commerce.common.order.timesheet.SingleDayTimesheetBuilder;
import com.omniesoft.commerce.common.order.timesheet.TimesheetBuilder;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import com.omniesoft.commerce.persistence.projection.order.OrderUserSummary;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.service.*;
import com.omniesoft.commerce.user.service.order.OrderService;
import com.omniesoft.commerce.user.service.organization.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.omniesoft.commerce.persistence.entity.enums.OrderStatus.*;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final ServiceRepository serviceRepository;

    private final ServiceTimingRepository serviceTimingRepository;

    private final ServicePriceRepository servicePriceRepository;

    private final OrderRepository orderRepository;

    private final ServicePriceConverter servicePriceConverter;

    private final OrderTimesheetService timesheetService;

    private final OrderConverter orderConverter;

    private final OrderPriceService orderPriceService;

    private final SubServiceRepository subServiceRepository;

    private final SubServicePriceRepository subServicePriceRepository;

    private final DiscountService discountService;


    @Override
    public Timesheet createTimesheet(UUID serviceId, LocalDate date) {

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

        timesheetService.insertAllOrdersNoDetails(timesheet, orders);
        return timesheet;
    }

    @Override
    public UUID makeOrder(SaveOrderDto order, UserEntity user) {

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

        timesheetService.insertAllOrdersNoDetails(timesheet, orders);

        TimesheetBuilder builder = new SingleDayTimesheetBuilder(timesheet);

        insertData(order.getSubServices(), start);

        OrderPeriod op = builder.orderPeriod(order.getStart(),
                order.getEnd(),
                null,
                null,
                null,
                null,
                orderConverter.transformSaveOrderSubServices(order.getSubServices())
        );

        if (builder.put(op)) {
            OrderEntity orderEntity = createOrderEntityWithoutPrices(order, user, PENDING_FOR_ADMIN);

            Optional<DiscountEntity> discountForService = discountService.findMaxServiceDiscount(orderEntity);
            Map<UUID, DiscountEntity> subServicesDiscounts = discountService.findMaxSubServicesDiscounts(orderEntity);
            chooseAndValidateDiscount(orderEntity, discountForService, subServicesDiscounts, serviceTiming);

            orderPriceService.calculatePrice(orderEntity, timesheet);

            orderRepository.save(orderEntity);
            return orderEntity.getId();

        } else {
            throw new UsefulException("Order schedule conflict", OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
        }
    }

    @Override
    public OrderPriceDto orderPrice(SaveOrderDto order, UserEntity user) {

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

        timesheetService.insertAllOrdersNoDetails(timesheet, orders);

        TimesheetBuilder builder = new SingleDayTimesheetBuilder(timesheet);

        insertData(order.getSubServices(), start);

        OrderPeriod op = builder.orderPeriod(order.getStart(),
                order.getEnd(),
                null,
                null,
                null,
                null,
                orderConverter.transformSaveOrderSubServices(order.getSubServices())
        );

        if (builder.put(op)) {
            OrderEntity orderEntity = createOrderEntityWithoutPrices(order, user, PENDING_FOR_ADMIN);

            Optional<DiscountEntity> discountForService = discountService.findMaxServiceDiscount(orderEntity);
            Map<UUID, DiscountEntity> subServicesDiscounts = discountService.findMaxSubServicesDiscounts(orderEntity);
            chooseAndValidateDiscount(orderEntity, discountForService, subServicesDiscounts, serviceTiming);

            orderPriceService.calculatePrice(orderEntity, timesheet);

            return orderConverter.mapToPriceDto(orderEntity);

        } else {
            throw new UsefulException("Order schedule conflict", OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
        }
    }

    @Override
    public OrderDto getOrderDetails(UUID serviceId, UUID orderId) {

        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        return orderConverter.mapToOrderDto(orderEntity);
    }

    @Override
    public void confirmOrder(UUID serviceId, UUID orderId, UserEntity user) {

        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        LocalDateTime start = LocalDateTime.of(orderEntity.getStart().toLocalDate(), LocalTime.of(0, 0));

        ServiceTimingEntity serviceTiming = serviceTimingRepository
                .findByServiceId(orderEntity.getService().getId(), start);

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

        timesheetService.insertAllOrdersNoDetails(timesheet, orders);

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
            if (orderEntity.getStatus() == PENDING_FOR_USER) {
                orderEntity.setStatus(CONFIRM_BY_USER);
                orderRepository.save(orderEntity);
            }
            throw new UsefulException("Current status not PENDING_FOR_USER",
                    OwnerModuleErrorCodes.ORDER_STATUS_NOT_CHANGEABLE);

        } else {
            throw new UsefulException(OwnerModuleErrorCodes.ORDER_TIMESHEET_CONFLICT);
        }
    }


    @Override
    public void cancelOrder(UUID serviceId, UUID orderId, UserEntity user) {
        OrderEntity orderEntity = orderRepository.findByIdAndServiceId(orderId, serviceId);

        if (orderEntity.getStatus() != PENDING_FOR_USER) {
            orderEntity.setStatus(CANCEL_BY_USER);
            orderEntity.setUpdateTime(LocalDateTime.now());
            orderRepository.save(orderEntity);
        } else throw new UsefulException("Current status DONE", OwnerModuleErrorCodes.ORDER_STATUS_NOT_CHANGEABLE);
    }

    @Override
    public List<LocalDate> fetchDateWithOrdersByStartDateAndEndDateAndUser(LocalDate startDate, LocalDate endDate, UserEntity userEntity) {
        List<LocalDateTime> allDayDateWithOrders = orderRepository.findAllDayDateWithOrders(userEntity.getId(), startDate, endDate);
        return allDayDateWithOrders.stream().map(localDateTime -> localDateTime.toLocalDate()).distinct().collect(Collectors.toList());
    }

    @Override
    public Page<OrderUserSummary> fetchOrdersByDateAndUser(LocalDate date, UserEntity userEntity, Pageable pageable) {
        return orderRepository.findAllByUserIdAndStartBetween(userEntity.getId(), LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX), pageable);
    }

    private void chooseAndValidateDiscount(OrderEntity order, Optional<DiscountEntity> discount,
                                           Map<UUID, DiscountEntity> subServicesDiscounts,
                                           ServiceTimingEntity serviceTiming) {

        if (discount.isPresent()) {
            if (order.getDiscountPercent() < discount.get().getPercent()) {
                order.setDiscount(discount.get());
                order.setDiscountPercent(discount.get().getPercent());
            }
        } else {
            order.setDiscountPercent(0D);
        }

        //validate discount limit
        if (order.getDiscountPercent() < 0 || order.getDiscountPercent() > serviceTiming.getMaxDiscount()) {
            throw new UsefulException("Order discount " + order.getDiscountPercent() +
                    " > max discount: " + serviceTiming.getMaxDiscount(),
                    OwnerModuleErrorCodes.DISCOUNT_VIOLATION_LIMITS);
        }

        if (order.getSubServices() != null) {
            for (OrderSubServicesEntity orderSubService : order.getSubServices()) {

                if (subServicesDiscounts.containsKey(orderSubService.getSubService().getId())) {
                    DiscountEntity discountEntity = subServicesDiscounts
                            .get(orderSubService.getSubService().getId());

                    if (orderSubService.getDiscountPercent() < discountEntity.getPercent()) {
                        orderSubService.setDiscount(discountEntity);
                        orderSubService.setDiscountPercent(discountEntity.getPercent());
                    }
                    //validate discount limit
                    else {
                        SubServicePriceEntity subServicePrice = subServicePriceRepository
                                .find(orderSubService.getSubService().getId(),
                                        order.getStart());

                        if (orderSubService.getDiscountPercent() < 0
                                || orderSubService.getDiscountPercent() > subServicePrice.getMaxDiscount()) {
                            throw new UsefulException("OrderSubService discount " +
                                    orderSubService.getDiscountPercent() +
                                    " > max discount: " + subServicePrice.getMaxDiscount(),
                                    OwnerModuleErrorCodes.DISCOUNT_VIOLATION_LIMITS);
                        }

                    }
                }
            }
        }
    }

    private void insertData(List<SaveOrderSubServices> subServices, LocalDateTime activeFrom) {
        for (SaveOrderSubServices subService : emptyIfNull(subServices)) {
            SubServicePriceEntity price = subServicePriceRepository.find(subService.getSubServiceId(), activeFrom);
            subService.setDuration(price.getDurationMillis());
            if (subService.getDiscountPercent() == null) {
                subService.setDiscountPercent(0D);
            }

        }

    }

    private OrderEntity createOrderEntityWithoutPrices(SaveOrderDto order, UserEntity customer, OrderStatus status) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStart(order.getStart());
        orderEntity.setEnd(order.getEnd());
        // TODO: 27.03.18 test for user
//        orderEntity.setDiscountPercent(order.getDiscountPercent());
        orderEntity.setStatus(status);
        orderEntity.setComment(order.getComment());

        orderEntity.setService(serviceRepository.findOne(order.getServiceId()));

        orderEntity.setUser(customer);
        orderEntity.setCustomerName(customer.getProfile().getFullName());
        orderEntity.setCustomerPhone(customer.getProfile().getPhone());

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
                    subServiceRepository
                            .findByIdAndServiceId(orderSubService.getSubServiceId(), order.getServiceId())
            );
            orderSubServiceEntity.setOrder(orderEntity);

            orderSubServiceEntity.setCount(orderSubService.getCount());
            orderSubServiceEntity.setDuration(orderSubService.getDuration());
            orderSubServiceEntity.setDiscountPercent(orderSubService.getDiscountPercent());

            result.add(orderSubServiceEntity);
        }

        return result;

    }
}
