package com.omniesoft.commerce.common.component.order.dto.price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
@Data
@NoArgsConstructor
public class OrderSubServicePriceDto {
    private UUID subServiceId;

    private Integer count;

    private Double discountPercent;

    private Double totalPrice;
}
