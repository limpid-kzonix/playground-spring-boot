package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.Timesheet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Vitalii Martynovskyi
 * @since 01.11.17
 */
public class SingleDayScheduleBuilder_EmptySchedule_Test extends AbstractSingleDayScheduleBuilderTest {

    private final Logger log = LoggerFactory.getLogger(SingleDayScheduleBuilder_EmptySchedule_Test.class);

    @BeforeClass
    public static void setExpected() {
        expected = Collections.singletonList(
                ClosePeriod.of(START_WORK_DAY, END_WORK_DAY)
        );
    }

    @Test
    public void emptyScheduleOneSlot() throws Exception {
        final int slotCount = 1;
        Timesheet timesheet = validOneDayScheduleBuilder(slotCount)
                .build(Collections.emptyMap());

        log.debug(timesheet.toString());
        assertEquals(slotCount, timesheet.size());

    }

    @Test
    public void emptyScheduleOneSlot2() throws Exception {
        final int slotCount = 1;

        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            schedule.put(dayOfWeek, Collections.emptyList());
        }

        Timesheet timesheet = validOneDayScheduleBuilder(slotCount).build(schedule);


        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());

    }

    @Test
    public void emptyScheduleManySlot() throws Exception {
        final int slotCount = 12;
        Timesheet timesheet = validOneDayScheduleBuilder(slotCount)
                .build(Collections.emptyMap());

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());

    }

    @Test
    public void emptyScheduleManySlot2() throws Exception {
        final int slotCount = 9;

        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            schedule.put(dayOfWeek, Collections.emptyList());
        }

        Timesheet timesheet = validOneDayScheduleBuilder(slotCount).build(schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());

    }

    private ScheduleBuilder validOneDayScheduleBuilder(int slotCount) {
        return new SingleDayScheduleBuilder()
                .setStart(START_WORK_DAY)
                .setEnd(END_WORK_DAY)
                .setMinDuration(true)
                .setSlotCount(slotCount)
                .setPauseDurationMillis(0L)
                .setOrderDurationMillis(360000L)
                .setMaxDiscount(0D);
    }
}