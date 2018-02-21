package com.omniesoft.commerce.user.service.order.impl;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.user.controller.order.payload.SaveOrderSubServices;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
@RequiredArgsConstructor
public class SaveSubServiceAdapter implements OrderSubService {

    private final SaveOrderSubServices subService;

    @Override
    public UUID getSubServiceId() {
        return subService.getSubServiceId();
    }

    @Override
    public Integer getCount() {
        return subService.getCount();
    }

    @Override
    public Long getDuration() {
        return subService.getDuration();
    }

    @Override
    public Double getDiscountPercent() {
        return subService.getDiscountPercent();
    }

    @Override
    public Double getSubServicePrice() {
        return null;
    }

    @Override
    public Double getSubServiceExpense() {
        return null;
    }

    @Override
    public Double getTotalPrice() {
        return null;
    }
}

