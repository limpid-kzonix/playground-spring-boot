package com.omniesoft.commerce.user.controller.order.payload;

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

    private Double subServicePrice;

    private Double subServiceExpense;

    private Double totalPrice;
}
