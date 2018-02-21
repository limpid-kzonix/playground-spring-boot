package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */

public class ServiceSettingsAdapter implements ScheduleSetting {
    @Override
    public LocalTime getStart() {
        return null;
    }

    @Override
    public LocalTime getEnd() {
        return null;
    }

    @Override
    public Double getPrice() {
        return null;
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
}

