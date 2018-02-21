package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.TimesheetPeriod;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Vitalii Martynovskyi
 * @since 27.10.17
 */
public class SingleDayScheduleBuilder implements ScheduleBuilder {

    private LocalDateTime start;
    private LocalDateTime end;
    private boolean minDuration = false;
    private int slotCount = 0;
    private long pauseDurationMillis = 0L;
    private long orderDurationMillis = 0L;
    private double maxDiscount = 0D;
    private Timesheet timesheet;

    public LocalDateTime getStart() {
        return start;
    }

    public ScheduleBuilder setStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public ScheduleBuilder setEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }

    public boolean isMinDuration() {
        return minDuration;
    }

    public ScheduleBuilder setMinDuration(boolean minDuration) {
        this.minDuration = minDuration;
        return this;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public ScheduleBuilder setSlotCount(int slotCount) {
        this.slotCount = slotCount;
        return this;
    }

    public long getPauseDurationMillis() {
        return pauseDurationMillis;
    }

    public ScheduleBuilder setPauseDurationMillis(long pauseDurationMillis) {
        this.pauseDurationMillis = pauseDurationMillis;
        return this;
    }

    public long getOrderDurationMillis() {
        return orderDurationMillis;
    }

    public ScheduleBuilder setOrderDurationMillis(long orderDurationMillis) {
        this.orderDurationMillis = orderDurationMillis;
        return this;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public ScheduleBuilder setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
        return this;
    }

    @Override
    public Timesheet build(Map<DayOfWeek, List<ScheduleSetting>> schedule) {


        ScheduleValidator.sameDate(start, end);

        timesheet = Timesheet.of(start, end, minDuration, slotCount,
                pauseDurationMillis, orderDurationMillis, maxDiscount);


        List<ScheduleSetting> scheduleSettings = schedule.get(start.getDayOfWeek());

        //if schedule not empty
        if (scheduleSettings != null && !scheduleSettings.isEmpty()) {

            //switch to modifiable collection
            scheduleSettings = new ArrayList<>(scheduleSettings);

            ScheduleValidator.scheduleSettings(scheduleSettings);

            scheduleSettings.sort(Comparator.naturalOrder());
            Iterator<? extends ScheduleSetting> iterator = scheduleSettings.iterator();

            ScheduleSetting prev, curr = null;
            LocalDateTime closeStart = start;
            LocalDateTime closeEnd;

            while (iterator.hasNext()) {
                prev = curr;
                curr = iterator.next();

                // putting first ClosePeriod
                if (prev == null) {

                    closeEnd = LocalDateTime.of(start.toLocalDate(), curr.getStart());
                    if (!closeStart.isEqual(closeEnd)) {
                        putInAllSlots(closeStart, closeEnd);
                    }

                    // putting single ClosePeriod
                    if (!iterator.hasNext()) {
                        closeStart = LocalDateTime.of(start.toLocalDate(), curr.getEnd());
                        if (!closeStart.isEqual(end)) {
                            putInAllSlots(closeStart, end);
                        }
                    }

                    // putting all other ClosePeriod
                } else {

                    if (!prev.getEnd().equals(curr.getStart())) {
                        closeStart = LocalDateTime.of(start.toLocalDate(), prev.getEnd());
                        closeEnd = LocalDateTime.of(start.toLocalDate(), curr.getStart());
                        if (!closeStart.isEqual(closeEnd)) {
                            putInAllSlots(closeStart, closeEnd);
                        }
                    }

                    // putting last ClosePeriod
                    if (!iterator.hasNext()) {
                        closeStart = LocalDateTime.of(start.toLocalDate(), curr.getEnd());
                        if (!closeStart.isEqual(end)) {
                            putInAllSlots(closeStart, end);
                        }
                    }
                }

            }
            timesheet.getScheduleSettings().addAll(scheduleSettings);

        }
        //if schedule empty
        else {
            putCloseAllDay();
        }
        return timesheet;
    }

    private void putCloseAllDay() {
        for (List<TimesheetPeriod> slot : timesheet.getTimesheet()) {
            slot.add(ClosePeriod.of(timesheet.getStart(), timesheet.getEnd()));
        }
    }

    private void putInAllSlots(LocalDateTime start, LocalDateTime end) {
        for (List<TimesheetPeriod> slot : timesheet.getTimesheet()) {
            slot.add(ClosePeriod.of(start, end));
        }
    }
}
