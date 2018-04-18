package com.omniesoft.commerce.common.component.order.dto.story;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderSubServicesStoryDto {
    private UUID subServiceId;
    private String subServiceName;
    private Integer count;
    private Double discountPercent;
    private Double totalPrice;
}
