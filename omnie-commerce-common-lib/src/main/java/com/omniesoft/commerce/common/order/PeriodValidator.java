package com.omniesoft.commerce.common.order;

import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 02.11.17
 */
public class PeriodValidator {

    public static void timesheetPeriod(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("ClosePeriod start | end == null");
        }
        if (start.isAfter(end) || start.equals(end)) {
            throw new IllegalArgumentException("ClosePeriod start > end");
        }
    }
}
