package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.Timesheet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Vitalii Martynovskyi
 * @since 01.11.17
 */
public class SingleDayScheduleBuilder_StartOfDayClose_Test extends AbstractSingleDayScheduleBuilderTest {
    private final Logger log = LoggerFactory.getLogger(SingleDayScheduleBuilder_StartOfDayClose_Test.class);

    @BeforeClass
    public static void setExpected() {
        expected = Collections.singletonList(
                ClosePeriod.of(START_WORK_DAY, LocalDateTime.of(YEAR, MONTH, DAY, 10, 0))
        );
    }

    @Test
    public void oneSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s = getScheduleSetting(
                LocalTime.of(10, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s.getDay(), Collections.singletonList(s));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void oneSettingManySlot() throws Exception {
        final int slotCount = 3;
        ScheduleSetting s = getScheduleSetting(
                LocalTime.of(10, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s.getDay(), Collections.singletonList(s));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void twoSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(11, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void twoSettingManySlot() throws Exception {
        final int slotCount = 3;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(10, 0),
                LocalTime.of(13, 0));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void threeSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(11, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(15, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void threeSettingManySlot() throws Exception {
        final int slotCount = 3;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(11, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(15, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }
}