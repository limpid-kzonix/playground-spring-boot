package com.omniesoft.commerce.common.notification.order.payload;

import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderNotifPl {
    private UUID orderId;
    private Integer orderNumber;
    private UUID customerId;
    private String customerName;
    private UUID orgId;
    private String orgName;
    private UUID serviceId;
    private String serviceName;
    private LocalDateTime start;
    private LocalDateTime end;

    public static OrderNotifPl map(OrderEntity orderEntity) {
        OrderNotifPl order = new OrderNotifPl();
        order.orderId = orderEntity.getId();
        order.orderNumber = orderEntity.getNumber();
        order.customerId = orderEntity.getUser().getId();
        order.customerName = orderEntity.getCustomerName();
        order.orgId = orderEntity.getService().getOrganization().getId();
        order.orgName = orderEntity.getService().getOrganization().getName();
        order.serviceId = orderEntity.getService().getId();
        order.serviceName = orderEntity.getService().getName();
        order.start = orderEntity.getStart();
        order.end = orderEntity.getEnd();
        return order;
    }
}
