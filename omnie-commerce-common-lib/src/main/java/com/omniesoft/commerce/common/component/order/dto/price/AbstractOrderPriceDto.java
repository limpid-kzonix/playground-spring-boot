package com.omniesoft.commerce.common.component.order.dto.price;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
@Data
@NoArgsConstructor
abstract class AbstractOrderPriceDto {
    private Double discountPercent;
    private Double totalPrice;
}
