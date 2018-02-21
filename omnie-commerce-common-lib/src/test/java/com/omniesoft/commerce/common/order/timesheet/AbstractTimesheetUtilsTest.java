package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.common.order.schedule.SingleDayScheduleBuilder;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 02.11.17
 */
public abstract class AbstractTimesheetUtilsTest {


    OrderPeriod getOrderPeriod(Timesheet timesheet, int startHour, int startMin, int endHour, int endMin) {
        return new SingleDayTimesheetBuilder(timesheet).orderPeriod(
                LocalDateTime.of(2010, 1, 1, startHour, startMin),
                LocalDateTime.of(2010, 1, 1, endHour, endMin),
                OrderStatus.DONE,
                UUID.randomUUID(),
                UUID.randomUUID(),
                "customerName",
                Collections.emptyList());
    }

    Timesheet timesheetMinDuration() {
        DayOfWeek dayOfWeek = LocalDate.of(2010, 1, 1).getDayOfWeek();

        ScheduleSetting s = getScheduleSetting(dayOfWeek);

        return new SingleDayScheduleBuilder()
                .setStart(LocalDateTime.of(2010, 1, 1, 10, 0))
                .setEnd(LocalDateTime.of(2010, 1, 1, 20, 0))
                .setMinDuration(true)
                .setSlotCount(1)
                .setPauseDurationMillis(0L)
                .setOrderDurationMillis(360000L)
                .setMaxDiscount(0D)
                .build(Collections.singletonMap(dayOfWeek, Collections.singletonList(s)));

    }

    Timesheet fixedDuration10_20_1H_1Slot() {
        DayOfWeek dayOfWeek = LocalDate.of(2010, 1, 1).getDayOfWeek();

        ScheduleSetting s = getScheduleSetting(dayOfWeek);

        return new SingleDayScheduleBuilder()
                .setStart(LocalDateTime.of(2010, 1, 1, 10, 0))
                .setEnd(LocalDateTime.of(2010, 1, 1, 20, 0))
                .setMinDuration(false)
                .setSlotCount(1)
                .setPauseDurationMillis(0L)
                .setOrderDurationMillis(60L * 60L * 1000L)
                .setMaxDiscount(0D)
                .build(Collections.singletonMap(dayOfWeek, Collections.singletonList(s)));

    }

    Timesheet fixedDuration10_20_1H_1Slot_WithClose() {
        DayOfWeek dayOfWeek = LocalDate.of(2010, 1, 1).getDayOfWeek();

        ScheduleSetting s = getScheduleSetting(dayOfWeek);

        return new SingleDayScheduleBuilder()
                .setStart(LocalDateTime.of(2010, 1, 1, 0, 0))
                .setEnd(LocalDateTime.of(2010, 1, 1, 23, 59))
                .setMinDuration(false)
                .setSlotCount(1)
                .setPauseDurationMillis(0L)
                .setOrderDurationMillis(60L * 60L * 1000L)
                .setMaxDiscount(0D)
                .build(Collections.singletonMap(dayOfWeek, Collections.singletonList(s)));

    }

    private ScheduleSetting getScheduleSetting(DayOfWeek dayOfWeek) {
        return new ScheduleSetting() {
            @Override
            public LocalTime getStart() {
                return LocalTime.of(10, 0);
            }

            @Override
            public LocalTime getEnd() {
                return LocalTime.of(20, 0);
            }

            @Override
            public Double getPrice() {
                return 37D;
            }

            @Override
            public Double getExpense() {
                return 11D;
            }

            @Override
            public DayOfWeek getDay() {
                return dayOfWeek;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return PriceUnit.PER_HOUR;
            }
        };
    }
}