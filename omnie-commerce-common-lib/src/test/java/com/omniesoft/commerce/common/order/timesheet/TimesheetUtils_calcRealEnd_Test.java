package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.OrderSubService;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Vitalii Martynovskyi
 * @since 06.11.17
 */
public class TimesheetUtils_calcRealEnd_Test extends AbstractTimesheetUtilsTest {
    @Test
    public void calcRealEndOneSubService() throws Exception {
        int count = 1;
        long additionalTImeMillis = 5000L;

        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 10, 0);
        OrderSubService oss = mock(OrderSubService.class);
        when(oss.getCount()).thenReturn(count);
        when(oss.getDuration()).thenReturn(additionalTImeMillis);

        LocalDateTime realEnd = TimesheetUtils.calcRealEnd(end, Collections.singletonList(oss));

        assertEquals(end.plusSeconds((additionalTImeMillis / 1000) * count), realEnd);
    }

    @Test
    public void calcRealEndOneSubServiceCount() throws Exception {
        int count = 31;
        long additionalTImeMillis = 7000L;

        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 10, 0);
        OrderSubService oss = mock(OrderSubService.class);
        when(oss.getCount()).thenReturn(count);
        when(oss.getDuration()).thenReturn(additionalTImeMillis);

        LocalDateTime realEnd = TimesheetUtils.calcRealEnd(end, Collections.singletonList(oss));

        assertEquals(end.plusSeconds((additionalTImeMillis / 1000) * count), realEnd);
    }

    @Test
    public void calcRealEndMoreSubServices() throws Exception {
        int count = 1;
        long additionalTImeMillis = 4000L;

        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 10, 0);
        OrderSubService oss1 = mock(OrderSubService.class);
        OrderSubService oss2 = mock(OrderSubService.class);
        when(oss1.getCount()).thenReturn(count);
        when(oss1.getDuration()).thenReturn(additionalTImeMillis);
        when(oss2.getCount()).thenReturn(count);
        when(oss2.getDuration()).thenReturn(additionalTImeMillis);

        LocalDateTime realEnd = TimesheetUtils.calcRealEnd(end, Arrays.asList(oss1, oss2));

        assertEquals(end.plusSeconds((additionalTImeMillis / 1000) * count * 2), realEnd);
    }

    @Test
    public void calcRealEndMoreSubServicesCount() throws Exception {
        int count = 11;
        long additionalTImeMillis = 4000L;

        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 10, 0);
        OrderSubService oss1 = mock(OrderSubService.class);
        OrderSubService oss2 = mock(OrderSubService.class);
        when(oss1.getCount()).thenReturn(count);
        when(oss1.getDuration()).thenReturn(additionalTImeMillis);
        when(oss2.getCount()).thenReturn(count);
        when(oss2.getDuration()).thenReturn(additionalTImeMillis);

        LocalDateTime realEnd = TimesheetUtils.calcRealEnd(end, Arrays.asList(oss1, oss2));

        assertEquals(end.plusSeconds((additionalTImeMillis / 1000) * count * 2), realEnd);
    }

}
