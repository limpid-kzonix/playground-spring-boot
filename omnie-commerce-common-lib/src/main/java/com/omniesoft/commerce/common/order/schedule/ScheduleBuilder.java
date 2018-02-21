package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.Timesheet;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Martynovskyi
 * @since 26.10.17
 */
public interface ScheduleBuilder {
    LocalDateTime getStart();

    ScheduleBuilder setStart(LocalDateTime start);

    LocalDateTime getEnd();

    ScheduleBuilder setEnd(LocalDateTime end);

    int getSlotCount();

    ScheduleBuilder setSlotCount(int slotCount);

    boolean isMinDuration();

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    ScheduleBuilder setMinDuration(boolean minDuration);

    long getPauseDurationMillis();

    ScheduleBuilder setPauseDurationMillis(long breakDurationMillis);

    long getOrderDurationMillis();

    ScheduleBuilder setOrderDurationMillis(long orderDurationMillis);

    double getMaxDiscount();

    ScheduleBuilder setMaxDiscount(double maxDiscount);

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    Timesheet build(Map<DayOfWeek, List<ScheduleSetting>> schedule);
}
