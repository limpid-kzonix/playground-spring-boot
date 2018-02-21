package com.omniesoft.commerce.persistence.projection.order;

import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.projection.service.ServiceSummary;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderUserSummary {

    UUID getId();

    Integer getNumber();

    ServiceSummary getService();

    OrderStatus getStatus();

    LocalDateTime getStart();

    LocalDateTime getEnd();

    String getComment();

    Double getTotalPrice();

    LocalDateTime getCreateTime();

}
