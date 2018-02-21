package com.omniesoft.commerce.common.order;

import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 26.10.17
 */
public class Timesheet {

    final @Getter
    LocalDateTime start;
    final @Getter
    LocalDateTime end;
    final @Getter
    boolean minDuration;
    final @Getter
    int slotCount;
    final @Getter
    long pauseDurationMillis;
    final @Getter
    long orderDurationMillis;
    final @Getter
    double maxDiscount;
    final @Getter
    List<ScheduleSetting> scheduleSettings;
    final @Getter
    List<List<TimesheetPeriod>> timesheet;

    private Timesheet(LocalDateTime start,
                      LocalDateTime end,
                      boolean minDuration,
                      int slotCount,
                      long pauseDurationMillis,
                      long orderDurationMillis,
                      double maxDiscount, List<List<TimesheetPeriod>> timesheet) {

        this.start = start;
        this.end = end;
        this.minDuration = minDuration;
        this.slotCount = slotCount;
        this.pauseDurationMillis = pauseDurationMillis;
        this.orderDurationMillis = orderDurationMillis;
        this.maxDiscount = maxDiscount;
        this.timesheet = timesheet;
        this.scheduleSettings = new ArrayList<>();

    }

    public static Timesheet of(LocalDateTime start,
                               LocalDateTime end,
                               boolean minDuration,
                               int slotCount,
                               long pauseDurationMillis,
                               long orderDurationMillis,
                               double maxDiscount) throws IllegalArgumentException {

        TimesheetArgumentsValidator.newTimesheet(start, end, slotCount, pauseDurationMillis, orderDurationMillis, maxDiscount);

        List<List<TimesheetPeriod>> timesheet = new ArrayList<>();
        for (int i = 0; i < slotCount; i++) {
            timesheet.add(new ArrayList<>());
        }

        return new Timesheet(start, end, minDuration, slotCount,
                pauseDurationMillis, orderDurationMillis, maxDiscount, timesheet);
    }

    public int size() {
        int size = 0;
        for (List<TimesheetPeriod> period : timesheet) {
            size += period.size();
        }
        return size;
    }

    @Override
    public String toString() {
        return "Timesheet{" +
                "\n start              =" + start +
                ",\n end                =" + end +
                ",\n minDuration        =" + minDuration +
                ",\n slotCount          =" + slotCount +
                ",\n pauseDurationMillis=" + pauseDurationMillis +
                ",\n orderDurationMillis=" + orderDurationMillis +
                ",\n maxDiscount        =" + maxDiscount +
                ",\n scheduleSettings   :\n" + scheduleSettings +
                ",\n timesheet          :\n" + timesheet +
                '}';
    }
}
