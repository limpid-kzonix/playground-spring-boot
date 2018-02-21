package com.omniesoft.commerce.common.order.schedule;

import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Vitalii Martynovskyi
 * @since 30.10.17
 */
public interface ScheduleSetting extends Comparable {

    LocalTime getStart();

    LocalTime getEnd();

    Double getPrice();

    Double getExpense();

    DayOfWeek getDay();

    PriceUnit getPriceUnit();

    @Override
    default int compareTo(Object o) {
        ScheduleSetting other = (ScheduleSetting) o;
        return this.getStart().compareTo(other.getStart());
    }
}
