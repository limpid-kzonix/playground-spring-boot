package com.omniesoft.commerce.common.order;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 30.10.17
 */
public class TimesheetArgumentsValidator_NewTimesheet_Test {
    @Test(expected = UsefulException.class)
    public void startNullEndNull() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                null,
                null,
                1,
                1,
                1,
                1);
    }

    @Test(expected = UsefulException.class)
    public void EndNull() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.now(),
                null,
                1,
                1,
                1,
                1);
    }

    @Test(expected = UsefulException.class)
    public void startSameEnd() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                1,
                1,
                1,
                1);
    }

    @Test(expected = UsefulException.class)
    public void startAfterEnd() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2001, 1, 1, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                1,
                1,
                1,
                1);
    }

    @Test(expected = UsefulException.class)
    public void slotCountLess1() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 8, 0),
                LocalDateTime.of(2000, 1, 1, 10, 0),
                0,
                1,
                1,
                1);
    }

    @Test(expected = UsefulException.class)
    public void slotCountMore50() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 8, 0),
                LocalDateTime.of(2000, 1, 1, 10, 0),
                51,
                1,
                1,
                1);
    }

    @Test(expected = UsefulException.class)
    public void breakDurationLess0() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 8, 0),
                LocalDateTime.of(2000, 1, 1, 10, 0),
                3,
                -1,
                1000,
                1);
    }

    @Test(expected = UsefulException.class)
    public void orderDurationLess1() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 8, 0),
                LocalDateTime.of(2000, 1, 1, 10, 0),
                3,
                1000,
                0,
                1);
    }

    @Test(expected = UsefulException.class)
    public void maxDiscountLess0() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 8, 0),
                LocalDateTime.of(2000, 1, 1, 10, 0),
                2,
                1,
                1,
                -1);
    }

    @Test(expected = UsefulException.class)
    public void maxDiscountMore100() throws Exception {
        TimesheetArgumentsValidator.newTimesheet(
                LocalDateTime.of(2000, 1, 1, 8, 0),
                LocalDateTime.of(2000, 1, 1, 10, 0),
                2,
                1,
                1,
                101);
    }

}