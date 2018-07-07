package com.omniesoft.commerce.persistence.docs.documents.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrderNotification {
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
}
