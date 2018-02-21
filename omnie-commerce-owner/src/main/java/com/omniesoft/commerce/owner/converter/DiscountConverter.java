package com.omniesoft.commerce.owner.converter;

import com.omniesoft.commerce.owner.controller.organization.payload.DiscountDto;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 24.10.17
 */
public interface DiscountConverter {
    List<DiscountDto> discountsDto(List<DiscountEntity> discounts);

    DiscountDto discountDto(DiscountEntity discount);

}
