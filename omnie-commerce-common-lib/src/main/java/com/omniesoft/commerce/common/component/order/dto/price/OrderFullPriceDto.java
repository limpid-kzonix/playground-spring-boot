package com.omniesoft.commerce.common.component.order.dto.price;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderFullPriceDto extends AbstractOrderPriceDto {
    private Double servicePrice;

    private Double serviceExpense;

    private List<OrderSubServiceFullPriceDto> subServices;
}
