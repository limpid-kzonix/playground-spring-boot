package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import com.omniesoft.commerce.persistence.repository.service.SubServicePriceRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 19.12.17
 */
@Service
@RequiredArgsConstructor
public class OrderPriceServiceImpl implements OrderPriceService {

    private final SubServicePriceRepository subServicePriceRepository;

    @Override
    public void calculatePrice(OrderEntity order,
                               Timesheet timesheet) {


        List<PricePeriod> pricePeriods = splitOrderOnPricePeriods(order, timesheet);

        double servicePrice = 0D;
        double expense = 0D;


        for (PricePeriod pricePeriod : pricePeriods) {
            Duration orderDuration = Duration.between(pricePeriod.getStart(), pricePeriod.getEnd());
            ScheduleSetting setting = pricePeriod.getValue();
            double pricePerSecond = calculatePerSecondValue(setting.getPriceUnit(), setting.getPrice());
            double expensePerSecond = calculatePerSecondValue(setting.getPriceUnit(), setting.getExpense());

            servicePrice += orderDuration.getSeconds() * pricePerSecond;
            expense += orderDuration.getSeconds() * expensePerSecond;
        }

        double orderPrice = (servicePrice / 100) * (100 - order.getDiscountPercent());

        order.setServiceExpense(expense);
        order.setServicePrice(servicePrice);
        order.setTotalPrice(orderPrice);

        if (order.getSubServices() != null) {
            for (OrderSubServicesEntity orderSubServices : order.getSubServices()) {
                SubServicePriceEntity subServicePriceEntity = subServicePriceRepository
                        .findBySubServiceIdAndActiveFrom(orderSubServices.getSubService().getId(), order.getStart());

                double subServicePrice = orderSubServices.getCount() * subServicePriceEntity.getPrice();
                double subServiceExpense = orderSubServices.getCount() * subServicePriceEntity.getExpense();
                double subServiceTotalPrice = (subServicePrice / 100) * (100 - orderSubServices.getDiscountPercent());

                orderSubServices.setSubServicePrice(subServicePrice);
                orderSubServices.setSubServiceExpense(subServiceExpense);
                orderSubServices.setTotalPrice(subServiceTotalPrice);

            }
        }
    }

    public List<PricePeriod> splitOrderOnPricePeriods(OrderEntity order, Timesheet timesheet) {
        List<PricePeriod> result = new ArrayList<>();
        for (ScheduleSetting setting : timesheet.getScheduleSettings()) {

            LocalDateTime periodStart = LocalDateTime.of(order.getStart().toLocalDate(), setting.getStart());
            LocalDateTime periodEnd = LocalDateTime.of(order.getEnd().toLocalDate(), setting.getEnd());

            //check collisions
            if (periodEnd.isAfter(order.getStart()) && periodStart.isBefore(order.getEnd())) {

                PricePeriod pricePeriod = new PricePeriod();
                pricePeriod.setStart(periodStart.isBefore(order.getStart()) ? order.getStart() : periodStart);
                pricePeriod.setEnd(periodEnd.isAfter(order.getEnd()) ? order.getEnd() : periodEnd);

                pricePeriod.setValue(setting);

                result.add(pricePeriod);

            }
        }
        return result;
    }

    private Double calculatePerSecondValue(PriceUnit unit, Double value) {
        switch (unit) {
            case PER_HOUR: {
                return value / 3600;
            }
            case PER_SECOND: {
                return value;
            }
            case PER_MINUTE: {
                return value / 60;
            }
        }
        return 0D;
    }

    @Data
    @NoArgsConstructor
    class PricePeriod {
        private LocalDateTime start;
        private LocalDateTime end;
        private ScheduleSetting value;

    }
}
