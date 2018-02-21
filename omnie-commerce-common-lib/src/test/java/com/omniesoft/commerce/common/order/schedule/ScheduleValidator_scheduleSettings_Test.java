package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Vitalii Martynovskyi
 * @since 30.10.17
 */
public class ScheduleValidator_scheduleSettings_Test {

    @Test(expected = IllegalArgumentException.class)
    public void oneSettingStartEqualEnd() throws Exception {
        ScheduleSetting s = mock(ScheduleSetting.class);
        when(s.getDay()).thenReturn(DayOfWeek.MONDAY);
        when(s.getExpense()).thenReturn(22D);
        when(s.getPriceUnit()).thenReturn(PriceUnit.PER_HOUR);
        when(s.getPrice()).thenReturn(36D);

        when(s.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s.getEnd()).thenReturn(LocalTime.of(10, 0));

        ScheduleValidator.scheduleSettings(Collections.singletonList(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void oneSettingStartAfterEnd() throws Exception {
        ScheduleSetting s = mock(ScheduleSetting.class);
        when(s.getDay()).thenReturn(DayOfWeek.MONDAY);
        when(s.getExpense()).thenReturn(22D);
        when(s.getPriceUnit()).thenReturn(PriceUnit.PER_HOUR);
        when(s.getPrice()).thenReturn(36D);

        when(s.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s.getEnd()).thenReturn(LocalTime.of(9, 0));

        ScheduleValidator.scheduleSettings(Collections.singletonList(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void oneSettingDayNull() throws Exception {
        ScheduleSetting s = mock(ScheduleSetting.class);
        when(s.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s.getEnd()).thenReturn(LocalTime.of(18, 0));
        when(s.getExpense()).thenReturn(22D);
        when(s.getPriceUnit()).thenReturn(PriceUnit.PER_HOUR);
        when(s.getPrice()).thenReturn(36D);

        when(s.getDay()).thenReturn(null);

        ScheduleValidator.scheduleSettings(Collections.singletonList(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void oneSettingPriceNegative() throws Exception {
        ScheduleSetting s = mock(ScheduleSetting.class);
        when(s.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s.getEnd()).thenReturn(LocalTime.of(18, 0));
        when(s.getDay()).thenReturn(DayOfWeek.MONDAY);
        when(s.getExpense()).thenReturn(22D);
        when(s.getPriceUnit()).thenReturn(PriceUnit.PER_HOUR);

        when(s.getPrice()).thenReturn(-1D);

        ScheduleValidator.scheduleSettings(Collections.singletonList(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void oneSettingExpenseNegative() throws Exception {
        ScheduleSetting s = mock(ScheduleSetting.class);
        when(s.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s.getEnd()).thenReturn(LocalTime.of(18, 0));
        when(s.getDay()).thenReturn(DayOfWeek.MONDAY);
        when(s.getPriceUnit()).thenReturn(PriceUnit.PER_HOUR);
        when(s.getPrice()).thenReturn(36D);

        when(s.getExpense()).thenReturn(-1D);

        ScheduleValidator.scheduleSettings(Collections.singletonList(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void oneSettingPriceUnitNull() throws Exception {
        ScheduleSetting s = mock(ScheduleSetting.class);
        when(s.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s.getEnd()).thenReturn(LocalTime.of(18, 0));
        when(s.getDay()).thenReturn(DayOfWeek.MONDAY);
        when(s.getExpense()).thenReturn(22D);
        when(s.getPrice()).thenReturn(36D);

        when(s.getPriceUnit()).thenReturn(null);

        ScheduleValidator.scheduleSettings(Collections.singletonList(s));

    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingSameTime() throws Exception {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));

    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingFirstEndAfter() throws Exception {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(12, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(11, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));
    }

    @Test
    public void twoSettingFirstEndSame() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(11, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(11, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));

    }

    @Test
    public void twoSettingWrongOrder() {
        ScheduleSetting s1 = new ScheduleSetting() {
            @Override
            public LocalTime getStart() {
                return LocalTime.of(10, 0);
            }

            @Override
            public LocalTime getEnd() {
                return LocalTime.of(12, 0);
            }

            @Override
            public Double getPrice() {
                return 36D;
            }

            @Override
            public Double getExpense() {
                return 24D;
            }

            @Override
            public DayOfWeek getDay() {
                return DayOfWeek.MONDAY;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return PriceUnit.PER_HOUR;
            }
        };

        ScheduleSetting s2 = new ScheduleSetting() {
            @Override
            public LocalTime getStart() {
                return LocalTime.of(12, 0);
            }

            @Override
            public LocalTime getEnd() {
                return LocalTime.of(18, 0);
            }

            @Override
            public Double getPrice() {
                return 36D;
            }

            @Override
            public Double getExpense() {
                return 24D;
            }

            @Override
            public DayOfWeek getDay() {
                return DayOfWeek.MONDAY;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return PriceUnit.PER_HOUR;
            }
        };

        ScheduleValidator.scheduleSettings(Arrays.asList(s2, s1));

    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingDifferentDay() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(11, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(11, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s2);
        when(s2.getDay()).thenReturn(DayOfWeek.SATURDAY);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));

    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingSameStart() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(9, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(11, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(9, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingSameStart1() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(9, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(15, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(9, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(12, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingSameEnd() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(9, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(15, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(12, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(15, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void twoSettingSameEnd1() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(12, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(15, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(9, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(15, 0));
        mockDefaultSettings(s2);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2));
    }

    @Test
    public void threeSetting() {
        ScheduleSetting s1 = mock(ScheduleSetting.class);
        when(s1.getStart()).thenReturn(LocalTime.of(10, 0));
        when(s1.getEnd()).thenReturn(LocalTime.of(11, 0));
        mockDefaultSettings(s1);

        ScheduleSetting s2 = mock(ScheduleSetting.class);
        when(s2.getStart()).thenReturn(LocalTime.of(11, 0));
        when(s2.getEnd()).thenReturn(LocalTime.of(14, 0));
        mockDefaultSettings(s2);

        ScheduleSetting s3 = mock(ScheduleSetting.class);
        when(s3.getStart()).thenReturn(LocalTime.of(14, 0));
        when(s3.getEnd()).thenReturn(LocalTime.of(18, 0));
        mockDefaultSettings(s3);

        ScheduleValidator.scheduleSettings(Arrays.asList(s1, s2, s3));

    }

    private void mockDefaultSettings(ScheduleSetting setting) {
        when(setting.getDay()).thenReturn(DayOfWeek.MONDAY);
        when(setting.getExpense()).thenReturn(22D);
        when(setting.getPrice()).thenReturn(36D);
        when(setting.getPriceUnit()).thenReturn(PriceUnit.PER_HOUR);
    }

}
