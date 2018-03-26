package com.omniesoft.commerce.common.component.order.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Vitalii Martynovskyi
 * @since 19.12.17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubServicesWithPricesDto extends OrderSubServicesDto {
    private Double subServicePrice;
    private Double subServiceExpense;
}
