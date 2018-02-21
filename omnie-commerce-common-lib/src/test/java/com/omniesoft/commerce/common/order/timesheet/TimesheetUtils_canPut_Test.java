package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.TimesheetPeriod;
import com.omniesoft.commerce.common.order.schedule.ClosePeriod;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Vitalii Martynovskyi
 * @since 06.11.17
 */
public class TimesheetUtils_canPut_Test extends AbstractTimesheetUtilsTest {

    private final Logger log = LoggerFactory.getLogger(TimesheetUtils_canPut_Test.class);

    private OrderPeriod getOrderPeriod(int startHour, int startMin, int endHour, int endMin) {
        return OrderPeriod.of(
                LocalDateTime.of(2010, 1, 1, startHour, startMin),
                LocalDateTime.of(2010, 1, 1, endHour, endMin),
                LocalDateTime.of(2010, 1, 1, endHour, endMin),
                OrderStatus.DONE,
                UUID.randomUUID(),
                UUID.randomUUID(),
                "customerName",
                Collections.emptyList());
    }

    private ClosePeriod getClosePeriod_from5hTo10h() {
        return ClosePeriod.of(
                LocalDateTime.of(2010, 1, 1, 5, 0),
                LocalDateTime.of(2010, 1, 1, 10, 0));
    }


    @Test
    public void emptySlot() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        int startHour = 10, startMin = 0;
        int endHour = 11, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        boolean actualResult = TimesheetUtils.canPut(slot, op, 0L);

        logResult(slot, op, actualResult);
        assertTrue(actualResult);
    }


    @Test
    public void collision_endClose() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getClosePeriod_from5hTo10h());

        int startHour = 9, startMin = 55;
        int endHour = 10, endMin = 55;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        boolean actualResult = TimesheetUtils.canPut(slot, op, 0L);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    @Test
    public void collision_startClose() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getClosePeriod_from5hTo10h());

        int startHour = 4, startMin = 5;
        int endHour = 5, endMin = 5;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        boolean actualResult = TimesheetUtils.canPut(slot, op, 0L);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    @Test
    public void collision_insideClose() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getClosePeriod_from5hTo10h());

        int startHour = 6, startMin = 0;
        int endHour = 7, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        boolean actualResult = TimesheetUtils.canPut(slot, op, 0L);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    @Test
    public void put_withPauseBeforeClose() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getClosePeriod_from5hTo10h());

        int startHour = 4, startMin = 0;
        int endHour = 5, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause5min = 5L * 60L * 1000L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause5min);

        logResult(slot, op, actualResult);
        assertTrue(actualResult);
    }

    @Test
    public void put_withPauseAfterClose() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getClosePeriod_from5hTo10h());

        int startHour = 10, startMin = 0;
        int endHour = 11, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause5min = 5L * 60L * 1000L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause5min);

        logResult(slot, op, actualResult);
        assertTrue(actualResult);
    }

    @Test
    public void insert_afterOrder() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getOrderPeriod(10, 0, 11, 0));

        int startHour = 11, startMin = 0;
        int endHour = 12, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause0min = 0L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause0min);

        logResult(slot, op, actualResult);
        assertTrue(actualResult);
    }

    @Test
    public void collision_endOrder() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getOrderPeriod(10, 0, 11, 0));

        int startHour = 10, startMin = 59;
        int endHour = 11, endMin = 59;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause0min = 0L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause0min);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    @Test
    public void collision_startOrder() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getOrderPeriod(10, 0, 11, 0));

        int startHour = 9, startMin = 1;
        int endHour = 10, endMin = 1;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause0min = 0L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause0min);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    @Test
    public void pauseCollision_afterOrder() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getOrderPeriod(10, 0, 11, 0));

        int startHour = 11, startMin = 0;
        int endHour = 12, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause5min = 5L * 60L * 1000L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause5min);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    @Test
    public void pauseCollision_beforeOrder() throws Exception {
        List<TimesheetPeriod> slot = new ArrayList<>();

        slot.add(getOrderPeriod(10, 0, 11, 0));

        int startHour = 9, startMin = 0;
        int endHour = 10, endMin = 0;
        OrderPeriod op = getOrderPeriod(startHour, startMin, endHour, endMin);

        long pause5min = 5L * 60L * 1000L;
        boolean actualResult = TimesheetUtils.canPut(slot, op, pause5min);

        logResult(slot, op, actualResult);
        assertFalse(actualResult);
    }

    private void logResult(List<TimesheetPeriod> slot, OrderPeriod op, boolean actualResult) {
        if (actualResult) {
            slot.add(op);
            log.debug(slot.toString());
        }
    }
}
