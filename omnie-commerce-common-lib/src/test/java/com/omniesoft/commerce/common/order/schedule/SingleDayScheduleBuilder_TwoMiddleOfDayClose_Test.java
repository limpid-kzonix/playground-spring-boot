package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.common.order.Timesheet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Vitalii Martynovskyi
 * @since 01.11.17
 */
public class SingleDayScheduleBuilder_TwoMiddleOfDayClose_Test extends AbstractSingleDayScheduleBuilderTest {
    private final Logger log = LoggerFactory.getLogger(SingleDayScheduleBuilder_TwoMiddleOfDayClose_Test.class);

    @BeforeClass
    public static void setExpected() {
        expected = Arrays.asList(
                ClosePeriod.of(
                        LocalDateTime.of(YEAR, MONTH, DAY, 12, 0),
                        LocalDateTime.of(YEAR, MONTH, DAY, 13, 0)),
                ClosePeriod.of(
                        LocalDateTime.of(YEAR, MONTH, DAY, 15, 0),
                        LocalDateTime.of(YEAR, MONTH, DAY, 16, 0))
        );
    }

    @Test
    public void threeSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(12, 0));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(16, 0),
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
                LocalTime.of(0, 0),
                LocalTime.of(12, 0));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void fourSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(10, 30));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(10, 30),
                LocalTime.of(12, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s4 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3, s4));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void fourSettingManySlot() throws Exception {
        final int slotCount = 3;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(10, 30));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(10, 30),
                LocalTime.of(12, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s4 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3, s4));


        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void fiveSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(10, 30));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(10, 30),
                LocalTime.of(12, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(14, 0));
        ScheduleSetting s4 = getScheduleSetting(
                LocalTime.of(14, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s5 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3, s4, s5));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void fiveSettingManySlot() throws Exception {
        final int slotCount = 3;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(10, 30));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(10, 30),
                LocalTime.of(12, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(14, 0));
        ScheduleSetting s4 = getScheduleSetting(
                LocalTime.of(14, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s5 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3, s4, s5));


        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void sixSettingOneSlot() throws Exception {
        final int slotCount = 1;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(10, 30));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(10, 30),
                LocalTime.of(12, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(14, 0));
        ScheduleSetting s4 = getScheduleSetting(
                LocalTime.of(14, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s5 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(18, 0));
        ScheduleSetting s6 = getScheduleSetting(
                LocalTime.of(18, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3, s4, s5, s6));

        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }

    @Test
    public void sixSettingManySlot() throws Exception {
        final int slotCount = 3;
        ScheduleSetting s1 = getScheduleSetting(
                LocalTime.of(0, 0),
                LocalTime.of(10, 30));
        ScheduleSetting s2 = getScheduleSetting(
                LocalTime.of(10, 30),
                LocalTime.of(12, 0));
        ScheduleSetting s3 = getScheduleSetting(
                LocalTime.of(13, 0),
                LocalTime.of(14, 0));
        ScheduleSetting s4 = getScheduleSetting(
                LocalTime.of(14, 0),
                LocalTime.of(15, 0));
        ScheduleSetting s5 = getScheduleSetting(
                LocalTime.of(16, 0),
                LocalTime.of(18, 0));
        ScheduleSetting s6 = getScheduleSetting(
                LocalTime.of(18, 0),
                LocalTime.of(23, 59));
        Map<DayOfWeek, List<ScheduleSetting>> schedule = new HashMap<>();
        schedule.put(s1.getDay(), Arrays.asList(s1, s2, s3, s4, s5, s6));


        Timesheet timesheet = validOneDayTimesheet(slotCount, schedule);

        log.debug(timesheet.toString());
        assertArrayEquals(expected.toArray(), timesheet.getTimesheet().get(0).toArray());
    }
}