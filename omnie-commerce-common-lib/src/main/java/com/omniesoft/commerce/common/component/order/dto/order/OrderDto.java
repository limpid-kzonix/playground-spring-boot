package com.omniesoft.commerce.common.component.order.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 12.12.17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractOrderDto {
    private List<OrderSubServicesDto> subServices;
}
