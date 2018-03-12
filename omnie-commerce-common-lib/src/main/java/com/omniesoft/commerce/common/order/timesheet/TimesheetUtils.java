package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.TimesheetPeriod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.omniesoft.commerce.common.order.TimesheetPeriodType.CLOSE;
import static com.omniesoft.commerce.common.order.TimesheetPeriodType.ORDER;
import static java.util.Comparator.naturalOrder;

/**
 * @author Vitalii Martynovskyi
 * @since 02.11.17
 */
public class TimesheetUtils {
    private static final Logger log = LoggerFactory.getLogger(TimesheetUtils.class);

    static LocalDateTime calcRealEnd(LocalDateTime actualEnd, List<OrderSubService> subServices) {

        if (subServices != null && !subServices.isEmpty()) {
            long additionalTimeMillis = 0L;
            for (OrderSubService subService : subServices) {
                log.debug("Subservice {}", subService.toString());
                additionalTimeMillis += subService.getCount() * subService.getDuration();
            }
            if (additionalTimeMillis > 0L) {
                return actualEnd.plusSeconds(additionalTimeMillis / 1000);
            }
        }
        return actualEnd;

    }

    static void sort(Timesheet timesheet) {
        timesheet.getTimesheet().forEach(t -> t.sort(naturalOrder()));
    }

    static void sort(List<TimesheetPeriod> periods) {
        periods.sort(naturalOrder());
    }

    static boolean isMeetTimesheetRules(Timesheet timesheet, OrderPeriod order) {
        return isWithinSchedule(timesheet, order) &&
                isCorrectDuration(timesheet, order);

    }

    private static boolean isWithinSchedule(Timesheet timesheet, OrderPeriod op) {
        return (timesheet.getStart().isBefore(op.getStart()) || timesheet.getStart().isEqual(op.getStart())) &&
                (timesheet.getEnd().isAfter(op.getRealEnd()) || timesheet.getEnd().isEqual(op.getRealEnd()));
    }

    private static boolean isCorrectDuration(Timesheet timesheet, OrderPeriod op) {
        long actualDurationMillis = Duration.between(op.getStart(), op.getEnd()).toMillis();
        log.debug("ActualDuration: " + actualDurationMillis);
        if (timesheet.isMinDuration()) {
            return actualDurationMillis >= timesheet.getOrderDurationMillis();
        } else {
            return actualDurationMillis == timesheet.getOrderDurationMillis();
        }
    }

    static boolean canPut(List<TimesheetPeriod> slot, OrderPeriod order, long pauseDurationMillis) {
        sort(slot);

        for (TimesheetPeriod period : slot) {
            if (CLOSE.equals(period.getType())) {

                if (period.getEnd().isAfter(order.getStart())) {

                    if (period.getStart().isBefore(order.getRealEnd())) {
                        return false;
                    }

                }
            }

            if (ORDER.equals(period.getType())) {
                OrderPeriod current = (OrderPeriod) period;
                LocalDateTime currentEndWithBreak = current.getRealEnd().plusSeconds(pauseDurationMillis / 1000);
                LocalDateTime orderEndWithBreak = order.getRealEnd().plusSeconds(pauseDurationMillis / 1000);

                if (currentEndWithBreak.isAfter(order.getStart())) {

                    if (current.getStart().isBefore(orderEndWithBreak)) {
                        return false;
                    }

                }
            }
        }

        return true;
    }
}



