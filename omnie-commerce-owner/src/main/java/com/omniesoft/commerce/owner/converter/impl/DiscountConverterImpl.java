package com.omniesoft.commerce.owner.converter.impl;

import com.omniesoft.commerce.owner.controller.organization.payload.DiscountDto;
import com.omniesoft.commerce.owner.converter.DiscountConverter;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 24.10.17
 */
@Service
@AllArgsConstructor
public class DiscountConverterImpl implements DiscountConverter {
    private final ModelMapper mapper;


    @Override
    public List<DiscountDto> discountsDto(List<DiscountEntity> discounts) {

        if (discounts != null) {

            java.lang.reflect.Type targetType = new TypeToken<List<DiscountDto>>() {
            }.getType();
            return mapper.map(discounts, targetType);
        }
        return null;
    }

    @Override
    public DiscountDto discountDto(DiscountEntity discount) {

        if (discount != null)

        {
            return mapper.map(discount, DiscountDto.class);
        }
        return null;
    }
}
