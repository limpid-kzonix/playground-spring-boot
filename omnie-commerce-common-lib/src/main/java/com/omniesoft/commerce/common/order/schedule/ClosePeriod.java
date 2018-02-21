package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.PeriodValidator;
import com.omniesoft.commerce.common.order.TimesheetPeriod;
import com.omniesoft.commerce.common.order.TimesheetPeriodType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Vitalii Martynovskyi
 * @since 27.10.2017
 */
public class ClosePeriod implements TimesheetPeriod {
    private static final TimesheetPeriodType TYPE = TimesheetPeriodType.CLOSE;

    private final LocalDateTime start;
    private final LocalDateTime end;


    private ClosePeriod(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static ClosePeriod of(LocalDateTime start, LocalDateTime end) {
        PeriodValidator.timesheetPeriod(start, end);
        return new ClosePeriod(start, end);
    }

    @Override
    public String toString() {
        return getType() + ": [" + start.toString() + "->" + end.toString() + "] \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClosePeriod that = (ClosePeriod) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public TimesheetPeriodType getType() {
        return TYPE;
    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public LocalDateTime getEnd() {
        return end;
    }
}
