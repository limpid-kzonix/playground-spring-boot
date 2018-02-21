package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.common.order.PeriodValidator;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.TimesheetPeriod;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 27.10.17
 */
public class SingleDayTimesheetBuilder implements TimesheetBuilder {

    private Timesheet timesheet;

    public SingleDayTimesheetBuilder(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public OrderPeriod orderPeriod(LocalDateTime start,
                                   LocalDateTime end,
                                   OrderStatus status,
                                   UUID orderId,
                                   UUID customerId,
                                   String customerName,
                                   List<OrderSubService> subServices) {

        PeriodValidator.timesheetPeriod(start, end);

        LocalDateTime realEnd = TimesheetUtils.calcRealEnd(end, subServices);

        return new OrderPeriod(start, end, realEnd, status, orderId, customerId, customerName, subServices);

    }


    @Override
    public boolean put(OrderPeriod order) {
        TimesheetUtils.sort(timesheet);

        if (TimesheetUtils.isMeetTimesheetRules(timesheet, order)) {

            for (List<TimesheetPeriod> slot : timesheet.getTimesheet()) {
                if (TimesheetUtils.canPut(slot, order, timesheet.getPauseDurationMillis())) {
                    slot.add(order);
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void putWithoutRules(OrderPeriod order) {

        timesheet.getTimesheet().get(0).add(order);
    }

}
