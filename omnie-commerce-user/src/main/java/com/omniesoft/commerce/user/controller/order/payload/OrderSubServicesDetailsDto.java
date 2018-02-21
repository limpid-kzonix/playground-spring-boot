package com.omniesoft.commerce.user.controller.order.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 19.12.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubServicesDetailsDto {
    private UUID id;
    private UUID subServiceId;
    private Integer count;
    private Long duration;
    private Double discountPercent;
    private Double totalPrice;
}
