package com.omniesoft.commerce.common.order;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Vitalii Martynovskyi
 * @since 31.10.17
 */

public class TimesheetTest {


    @Test
    public void factoryMethodOf() throws Exception {
        final LocalDateTime start = LocalDateTime.of(2010, 5, 10, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2010, 5, 10, 23, 59);
        final boolean minDuration = false;
        final int slotCount = 35;
        final long pauseDurationMillis = 35478L;
        final long orderDurationMillis = 2067438L;
        final double maxDiscount = 89D;

        Timesheet timesheet = Timesheet.of(
                start,
                end,
                minDuration,
                slotCount,
                pauseDurationMillis,
                orderDurationMillis,
                maxDiscount);

        assertNotNull(timesheet);
        assertEquals(start, timesheet.getStart());
        assertEquals(end, timesheet.getEnd());
        assertEquals(minDuration, timesheet.isMinDuration());
        assertEquals(slotCount, timesheet.getSlotCount());
        assertEquals(pauseDurationMillis, timesheet.getPauseDurationMillis());
        assertEquals(orderDurationMillis, timesheet.getOrderDurationMillis());
        assertEquals(maxDiscount, timesheet.getMaxDiscount(), 0.001);
        assertEquals(slotCount, timesheet.getTimesheet().size());
    }

}