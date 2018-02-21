package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.Timesheet;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Vitalii Martynovskyi
 * @since 06.11.17
 */
public class TimesheetUtils_isMeetTimesheetRules_Test extends AbstractTimesheetUtilsTest {

    private final Logger log = LoggerFactory.getLogger(TimesheetUtils_isMeetTimesheetRules_Test.class);

    @Test
    public void isMeetTimesheetRules_Start() throws Exception {
        Timesheet timesheet = timesheetMinDuration();
        OrderPeriod op = getOrderPeriod(timesheet, 10, 0, 11, 0);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet, op);

        assertTrue(actualResult);
    }

    @Test
    public void isMeetTimesheetRules_StartCollision() throws Exception {
        Timesheet timesheet = timesheetMinDuration();
        OrderPeriod op = getOrderPeriod(timesheet, 9, 59, 11, 0);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet, op);

        assertFalse(actualResult);
    }

    @Test
    public void isMeetTimesheetRules_End() throws Exception {
        Timesheet timesheet = timesheetMinDuration();
        OrderPeriod op = getOrderPeriod(timesheet, 19, 0, 20, 0);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet, op);

        assertTrue(actualResult);
    }

    @Test
    public void isMeetTimesheetRules_EndCollision() throws Exception {
        Timesheet timesheet = timesheetMinDuration();
        OrderPeriod op = getOrderPeriod(timesheet, 19, 0, 20, 1);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet, op);

        assertFalse(actualResult);
    }

    @Test
    public void isMeetTimesheetRules_orderDuration() throws Exception {
        Timesheet timesheet1h = fixedDuration10_20_1H_1Slot();
        OrderPeriod op = getOrderPeriod(timesheet1h, 19, 0, 20, 0);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet1h, op);

        assertTrue(actualResult);
    }

    @Test
    public void isMeetTimesheetRules_orderDurationLess() throws Exception {
        Timesheet timesheet1h = fixedDuration10_20_1H_1Slot();
        OrderPeriod op = getOrderPeriod(timesheet1h, 19, 0, 19, 20);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet1h, op);

        assertFalse(actualResult);
    }

    @Test
    public void isMeetTimesheetRules_orderDurationMore() throws Exception {
        Timesheet timesheet1h = fixedDuration10_20_1H_1Slot();
        OrderPeriod op = getOrderPeriod(timesheet1h, 15, 0, 16, 10);
        boolean actualResult = TimesheetUtils.isMeetTimesheetRules(timesheet1h, op);

        assertFalse(actualResult);
    }

}
