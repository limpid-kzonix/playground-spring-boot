package com.omniesoft.commerce.common.component.order.dto.order;

import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.12.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractOrderDto {
    private UUID id;

    private Integer number;

    private OrderStatus status;

    private LocalDateTime start;

    private LocalDateTime end;

    private String customerName;

    private String customerPhone;

    private String comment;

    private Double discountPercent;

    private Double totalPrice;

    private LocalDateTime createTime;

}
