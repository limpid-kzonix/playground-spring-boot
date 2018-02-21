package com.omniesoft.commerce.user.service.order.impl;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
@RequiredArgsConstructor
public class SubServiceEntityAdapter implements OrderSubService {

    private final OrderSubServicesEntity orderSubService;

    @Override
    public UUID getSubServiceId() {
        return orderSubService.getSubService().getId();
    }

    @Override
    public Integer getCount() {
        return orderSubService.getCount();
    }

    @Override
    public Long getDuration() {
        return orderSubService.getDuration();
    }

    @Override
    public Double getDiscountPercent() {
        return orderSubService.getDiscountPercent();
    }

    @Override
    public Double getSubServicePrice() {
        return orderSubService.getSubServicePrice();
    }

    @Override
    public Double getSubServiceExpense() {
        return orderSubService.getSubServiceExpense();
    }

    @Override
    public Double getTotalPrice() {
        return orderSubService.getTotalPrice();
    }
}

