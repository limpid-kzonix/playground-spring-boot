package com.omniesoft.commerce.common.order.schedule;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 01.11.17
 */
class ScheduleValidator {

    static void sameDate(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) throws IllegalArgumentException {
        LocalDate startDate = startTime.toLocalDate();
        LocalDate endDate = endTime.toLocalDate();
        // same day
        if (startDate.isEqual(endDate)) {
            return;
        }
        throw new IllegalArgumentException("startTime & endTime not in same date");
    }

    static void scheduleSettings(List<ScheduleSetting> scheduleSettings) {

        Collections.sort(scheduleSettings, Comparator.naturalOrder());

        ScheduleSetting prev, next = null;

        Iterator<? extends ScheduleSetting> iterator = scheduleSettings.iterator();

        while (iterator.hasNext()) {
            prev = next;
            next = iterator.next();

            validateSingleScheduleSetting(next);

            if (prev != null) {

                if (!next.getDay().equals(prev.getDay())) {
                    throw new IllegalArgumentException("Settings day not equal");
                }

                if (next.getStart().isBefore(prev.getEnd())) {
                    throw new IllegalArgumentException("Not valid chain of settings");
                }

            }

        }

    }

    private static void validateSingleScheduleSetting(ScheduleSetting setting) {
        if (setting.getStart().equals(setting.getEnd())) {
            throw new IllegalArgumentException("Invalid setting start == end");
        }

        if (setting.getStart().isAfter(setting.getEnd())) {
            throw new IllegalArgumentException("Invalid setting start > end");
        }

        if (setting.getDay() == null) {
            throw new IllegalArgumentException("Invalid setting day == null");
        }

        if (setting.getExpense() < 0) {
            throw new IllegalArgumentException("Invalid setting expense < 0");
        }

        if (setting.getPrice() < 0) {
            throw new IllegalArgumentException("Invalid setting price < 0");
        }

        if (setting.getPriceUnit() == null) {
            throw new IllegalArgumentException("Invalid setting priceUnit == null");
        }

    }
}
