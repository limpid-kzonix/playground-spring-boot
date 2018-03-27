package com.omniesoft.commerce.common.component.order.dto;

import com.omniesoft.commerce.common.order.OrderSubService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
@RequiredArgsConstructor
public class SaveFullSubServiceAdapter implements OrderSubService {

    private final SaveFullOrderSubServices subService;

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

//    @Override
//    public Double getSubServicePrice() {
//        return null;
//    }
//
//    @Override
//    public Double getSubServiceExpense() {
//        return null;
//    }
//
//    @Override
//    public Double getTotalPrice() {
//        return null;
//    }
}

