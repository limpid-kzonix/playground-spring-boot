package com.omniesoft.commerce.common.order.schedule;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 01.11.17
 */
public class ScheduleValidator_sameDate_Test {

    @Test
    public void sameDate() throws Exception {
        LocalDateTime start = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2010, 1, 1, 23, 59);
        ScheduleValidator.sameDate(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sameDate24h() throws Exception {
        LocalDateTime start = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2010, 1, 2, 0, 0);
        ScheduleValidator.sameDate(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void diffDate() throws Exception {
        LocalDateTime start = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2010, 1, 3, 0, 0);
        ScheduleValidator.sameDate(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void diffDateNextDay() throws Exception {
        LocalDateTime start = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2010, 1, 2, 12, 0);
        ScheduleValidator.sameDate(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void diffDateNextYear() throws Exception {
        LocalDateTime start = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2011, 1, 1, 12, 0);
        ScheduleValidator.sameDate(start, end);
    }
}