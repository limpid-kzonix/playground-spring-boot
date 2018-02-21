package com.omniesoft.commerce.common.order;

import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 07.04.2017
 */
public interface TimesheetPeriod extends Comparable {
    LocalDateTime getStart();

    LocalDateTime getEnd();

    TimesheetPeriodType getType();

    @Override
    default int compareTo(Object o) {
        if (o != null && o instanceof TimesheetPeriod) {
            TimesheetPeriod op = (TimesheetPeriod) o;
            return this.getStart().compareTo(op.getStart());
        }
        return 1;
    }
}
