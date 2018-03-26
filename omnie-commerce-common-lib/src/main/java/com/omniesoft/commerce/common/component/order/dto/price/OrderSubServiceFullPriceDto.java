package com.omniesoft.commerce.common.component.order.dto.price;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderSubServiceFullPriceDto extends OrderSubServicePriceDto {
    private Double subServicePrice;

    private Double subServiceExpense;
}
