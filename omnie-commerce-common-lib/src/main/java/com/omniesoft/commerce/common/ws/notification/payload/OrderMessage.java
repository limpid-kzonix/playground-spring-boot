package com.omniesoft.commerce.common.ws.notification.payload;

import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderMessage extends NotificationMessage {

    /**
     * @param userName May blank or null if it isn`t direct message (if you don`t know username of recipient)
     * @param order Use {@link OrderMessageFactory} for prepare {@link Order} instance
     */

    private String userName;
    private Order order;

    public OrderMessage(Order order) {

        this.order = order;
    }

    @Override
    public String getStompUserName() {

        return getUserName();
    }

    @Override
    public UUID getOrganizationId() {

        return getOrder().getService().getOrganization().getId();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Order {

    private UUID id;

    private User user;

    private Integer number;

    private Service service;

    private OrderStatus status;

    private LocalDateTime start;

    private LocalDateTime end;

    private String customerName;

    private String customerPhone;

    private String comment;

}

