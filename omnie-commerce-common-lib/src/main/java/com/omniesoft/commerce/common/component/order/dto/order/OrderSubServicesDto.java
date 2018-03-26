package com.omniesoft.commerce.common.component.order.dto.order;

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
public class OrderSubServicesDto {
    private UUID id;
    private UUID subServiceId;
    private Integer count;
    private Long duration;
    private Double discountPercent;
    private Double totalPrice;
}
