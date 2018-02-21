package com.omniesoft.commerce.common.order;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;

import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 27.10.17
 */
class TimesheetArgumentsValidator {

    static void newTimesheet(LocalDateTime start,
                             LocalDateTime end,
                             int slotCount,
                             long breakDurationMillis,
                             long orderDurationMillis,
                             double maxDiscount) throws IllegalArgumentException {

        if (start == null) {
            throw new UsefulException("start == null", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (end == null) {
            throw new UsefulException("end == null", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (start.isEqual(end)) {
            throw new UsefulException("start == end", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (end.isBefore(start)) {
            throw new UsefulException("start > end", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (slotCount < 1 || slotCount > 50) {
            throw new UsefulException("number of slotCount must be between 1 and 50", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (breakDurationMillis < 0) {
            throw new UsefulException("pauseDurationMillis < 0 ms", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (orderDurationMillis < 1) {
            throw new UsefulException("orderDurationMillis < 1 ms", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

        if (maxDiscount < 0 || maxDiscount > 100) {
            throw new UsefulException("maxDiscount must be between 0 and 100", OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
        }

    }
}
