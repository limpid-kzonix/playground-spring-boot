package com.omniesoft.commerce.user.service.order.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.common.order.schedule.SingleDayScheduleBuilder;
import com.omniesoft.commerce.common.order.timesheet.OrderPeriod;
import com.omniesoft.commerce.common.order.timesheet.SingleDayTimesheetBuilder;
import com.omniesoft.commerce.common.order.timesheet.TimesheetBuilder;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.user.service.order.OrderConverter;
import com.omniesoft.commerce.user.service.order.OrderTimesheetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 21.11.17
 */
@Service
@RequiredArgsConstructor
public class OrderTimesheetServiceImpl implements OrderTimesheetService {
    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderConverter orderConverter;

    public Timesheet buildSingleDaySchedule(UUID serviceId,
                                            LocalDate scheduleDate,
                                            ServiceTimingEntity serviceTiming,
                                            Map<DayOfWeek, List<ScheduleSetting>> schedule) {

        // set defaults, safe when serviceTiming == null
        LocalDateTime start = LocalDateTime.of(scheduleDate, LocalTime.of(0, 0));
        Long pauseMillis = 0L, durationMillis = 3600L;
        Integer slotCount = 1;
        Double maxDiscount = 0D;
        Boolean minDuration = false;
        if (serviceTiming != null) {
            slotCount = serviceTiming.getSlotCount();
            pauseMillis = serviceTiming.getPauseMillis();
            durationMillis = serviceTiming.getDurationMillis();
            maxDiscount = serviceTiming.getMaxDiscount();
            minDuration = serviceTiming.getMinDuration();
        } else {
            // not possible situation but we must be safe
            if (schedule.size() > 0) {
                log.warn("Service has problem with Timing, id: " + serviceId);
                throw new UsefulException(OwnerModuleErrorCodes.SERVICE_NOT_CONFIGURED);
            }
        }

        return new SingleDayScheduleBuilder()
                .setStart(start)
                .setEnd(LocalDateTime.of(scheduleDate, LocalTime.of(23, 59)))
                .setSlotCount(slotCount)
                .setMinDuration(minDuration)
                .setOrderDurationMillis(durationMillis)
                .setPauseDurationMillis(pauseMillis)
                .setMaxDiscount(maxDiscount)
                .build(schedule);
    }

    @Override
    public void insertAllOrders(Timesheet timesheet, List<OrderEntity> orders) {

        TimesheetBuilder timesheetBuilder = new SingleDayTimesheetBuilder(timesheet);

        for (OrderEntity order : orders) {
            OrderPeriod op = timesheetBuilder.orderPeriod(order.getStart(),
                    order.getEnd(),
                    null,
                    null,
                    null,
                    null,
                    orderConverter.transformOrderSubServices(order.getSubServices()));

            timesheetBuilder.put(op);

        }
    }
}
