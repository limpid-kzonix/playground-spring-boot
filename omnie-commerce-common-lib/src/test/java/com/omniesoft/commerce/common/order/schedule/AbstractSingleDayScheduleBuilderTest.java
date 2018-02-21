package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Martynovskyi
 * @since 01.11.17
 */
public class AbstractSingleDayScheduleBuilderTest {
    static final int YEAR = 2017;
    static final int MONTH = 11;
    static final int DAY = 6;

    static final LocalDateTime START_WORK_DAY = LocalDateTime.of(YEAR, MONTH, DAY, 0, 0);
    static final LocalDateTime END_WORK_DAY = LocalDateTime.of(YEAR, MONTH, DAY, 23, 59);
    static List<ClosePeriod> expected;

    ScheduleSetting getScheduleSetting(LocalTime start, LocalTime end) {
        return new ScheduleSetting() {
            private final double price = 36D;
            private final double expanse = 24D;
            private final DayOfWeek day = DayOfWeek.MONDAY;
            private final PriceUnit priceUnit = PriceUnit.PER_HOUR;

            @Override
            public LocalTime getStart() {
                return start;
            }

            @Override
            public LocalTime getEnd() {
                return end;
            }

            @Override
            public Double getPrice() {
                return price;
            }

            @Override
            public Double getExpense() {
                return expanse;
            }

            @Override
            public DayOfWeek getDay() {
                return day;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return priceUnit;
            }

            @Override
            public String toString() {
                return "{ " + getDay() + ": " + getStart().toString() + "->" + getEnd().toString() + "}\n";
            }
        };
    }

    Timesheet validOneDayTimesheet(int slotCount, Map<DayOfWeek, List<ScheduleSetting>> schedule) {
        return new SingleDayScheduleBuilder()
                .setStart(START_WORK_DAY)
                .setEnd(END_WORK_DAY)
                .setMinDuration(true)
                .setSlotCount(slotCount)
                .setPauseDurationMillis(0L)
                .setOrderDurationMillis(360000L)
                .setMaxDiscount(0D)
                .build(schedule);
    }

}
