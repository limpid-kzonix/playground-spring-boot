package com.omniesoft.commerce.user.controller.order.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
@Data
@NoArgsConstructor
public class OrderPriceDto {
    private Double discountPercent;

    private Double servicePrice;

    private Double serviceExpense;

    private Double totalPrice;

    private List<OrderSubServicePriceDto> subServices;
}
