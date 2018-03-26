package com.omniesoft.commerce.common.component.order.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 12.12.17
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderWithPricesDto extends AbstractOrderDto {

    private Double servicePrice;
    private Double serviceExpense;
    private LocalDateTime updateTime;

    private List<OrderSubServicesWithPricesDto> subServices;
}
