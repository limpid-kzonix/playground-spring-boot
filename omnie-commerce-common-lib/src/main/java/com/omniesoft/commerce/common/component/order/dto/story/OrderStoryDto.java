package com.omniesoft.commerce.common.component.order.dto.story;

import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderStoryDto {

    private OrderStatus status;

    private LocalDateTime start;

    private LocalDateTime end;

    private String customerName;

    private UUID serviceId;

    private String serviceName;

    private String customerPhone;

    private String comment;

    private Double discountPercent;

    private Double totalPrice;

    private LocalDateTime createTime;

    private List<OrderSubServicesStoryDto> subServices;
}
