package com.omniesoft.commerce.common.order;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 02.11.17
 */
public class PeriodValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void timesheetPeriodStartIsNull() throws Exception {
        PeriodValidator.timesheetPeriod(null, LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void timesheetPeriodEndIsnull() throws Exception {
        PeriodValidator.timesheetPeriod(LocalDateTime.now(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void timesheetPeriodStartAndEndEqual() throws Exception {
        LocalDateTime time = LocalDateTime.of(2010, 1, 1, 12, 44);
        PeriodValidator.timesheetPeriod(time, time);
    }

    @Test(expected = IllegalArgumentException.class)
    public void timesheetPeriodStartAfterEnd() throws Exception {
        LocalDateTime start = LocalDateTime.of(2010, 1, 1, 15, 20);
        LocalDateTime end = LocalDateTime.of(2010, 1, 1, 12, 44);
        PeriodValidator.timesheetPeriod(start, end);
    }

}