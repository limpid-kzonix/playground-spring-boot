package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.repository.service.SubServicePriceRepository;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Vitalii Martynovskyi
 * @since 19.12.17
 */
public class OrderPriceServiceImplTest {
    private final LocalDate date = LocalDate.of(2017, 12, 10);

    private final SubServicePriceRepository mockedRepo = mock(SubServicePriceRepository.class);

    @Test
    public void splitOrderOnPricePeriods_SingleSettings() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(14, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(15, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(singleScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_SingleSettings_SameStart() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(10, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(15, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(singleScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_SingleSettings_SameEnd() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(16, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(18, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(singleScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_SingleSettings_SameStartAndEnd() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(10, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(18, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(singleScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }


    @Test
    public void splitOrderOnPricePeriods_TwoSettings() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(11, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(13, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(twoScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(2, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_TwoSettings_SameStart() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(10, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(11, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(twoScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_TwoSettings_SameEnd() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(17, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(18, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(twoScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_TwoSettings_SameStartAndEnd() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(10, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(18, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(twoScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(2, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_TwoSettings_StartInSettingConnection() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(12, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(13, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(twoScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }


    @Test
    public void splitOrderOnPricePeriods_TwoSettings_EndInSettingConnection() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(11, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(12, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(twoScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(1, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }

    @Test
    public void splitOrderOnPricePeriods_ThreeSettings() {

        OrderEntity order = mock(OrderEntity.class);
        Timesheet timesheet = mock(Timesheet.class);

        when(order.getStart()).thenReturn(LocalDateTime.of(date, LocalTime.of(11, 0)));
        when(order.getEnd()).thenReturn(LocalDateTime.of(date, LocalTime.of(16, 0)));

        when(timesheet.getScheduleSettings()).thenReturn(threeScheduleSetting());

        List<OrderPriceServiceImpl.PricePeriod> pricePeriods = new OrderPriceServiceImpl(mockedRepo).
                splitOrderOnPricePeriods(order, timesheet);


        assertEquals(3, pricePeriods.size());

        assertEquals(Duration.between(order.getStart(), order.getEnd()),
                fullDuration(pricePeriods));

    }


    private Duration fullDuration(List<OrderPriceServiceImpl.PricePeriod> pricePeriods) {
        Duration accumulator = Duration.ZERO;

        for (OrderPriceServiceImpl.PricePeriod period : pricePeriods) {
            accumulator = accumulator.plus(Duration.between(period.getStart(), period.getEnd()));
        }
        return accumulator;
    }

    private List<ScheduleSetting> singleScheduleSetting() {

        List<ScheduleSetting> result = new ArrayList<>();
        ScheduleSetting setting = new ScheduleSetting() {
            @Override
            public LocalTime getStart() {
                return LocalTime.of(10, 0);
            }

            @Override
            public LocalTime getEnd() {
                return LocalTime.of(18, 0);
            }

            @Override
            public Double getPrice() {
                return 100D;
            }

            @Override
            public Double getExpense() {
                return null;
            }

            @Override
            public DayOfWeek getDay() {
                return null;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return null;
            }
        };
        result.add(setting);
        return result;
    }


    private List<ScheduleSetting> twoScheduleSetting() {

        List<ScheduleSetting> result = new ArrayList<>();
        ScheduleSetting setting = new ScheduleSetting() {
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
                return 100D;
            }

            @Override
            public Double getExpense() {
                return null;
            }

            @Override
            public DayOfWeek getDay() {
                return null;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return null;
            }
        };

        ScheduleSetting setting1 = new ScheduleSetting() {
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
                return 100D;
            }

            @Override
            public Double getExpense() {
                return null;
            }

            @Override
            public DayOfWeek getDay() {
                return null;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return null;
            }
        };

        result.add(setting);
        result.add(setting1);
        return result;
    }

    private List<ScheduleSetting> threeScheduleSetting() {

        List<ScheduleSetting> result = new ArrayList<>();
        ScheduleSetting setting = new ScheduleSetting() {
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
                return 100D;
            }

            @Override
            public Double getExpense() {
                return null;
            }

            @Override
            public DayOfWeek getDay() {
                return null;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return null;
            }
        };

        ScheduleSetting setting1 = new ScheduleSetting() {
            @Override
            public LocalTime getStart() {
                return LocalTime.of(12, 0);
            }

            @Override
            public LocalTime getEnd() {
                return LocalTime.of(15, 0);
            }

            @Override
            public Double getPrice() {
                return 100D;
            }

            @Override
            public Double getExpense() {
                return null;
            }

            @Override
            public DayOfWeek getDay() {
                return null;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return null;
            }
        };

        ScheduleSetting setting2 = new ScheduleSetting() {
            @Override
            public LocalTime getStart() {
                return LocalTime.of(15, 0);
            }

            @Override
            public LocalTime getEnd() {
                return LocalTime.of(18, 0);
            }

            @Override
            public Double getPrice() {
                return 100D;
            }

            @Override
            public Double getExpense() {
                return null;
            }

            @Override
            public DayOfWeek getDay() {
                return null;
            }

            @Override
            public PriceUnit getPriceUnit() {
                return null;
            }
        };

        result.add(setting);
        result.add(setting1);
        result.add(setting2);
        return result;
    }
}