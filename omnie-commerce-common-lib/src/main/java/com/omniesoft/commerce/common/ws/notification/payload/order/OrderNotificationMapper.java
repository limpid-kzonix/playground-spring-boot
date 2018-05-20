package com.omniesoft.commerce.common.ws.notification.payload.order;

import com.omniesoft.commerce.common.ws.notification.payload.EnhancedMessageFactory;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;

public class OrderNotificationMapper {

    private static Order create(OrderEntity orderEntity) {

        Order order = new Order();

        order.setId(orderEntity.getId());

        order.setComment(orderEntity.getComment());
        order.setCustomerName(orderEntity.getCustomerName());
        order.setCustomerPhone(orderEntity.getCustomerPhone());
        order.setEnd(orderEntity.getEnd());
        order.setStart(orderEntity.getStart());

        order.setService(EnhancedMessageFactory.extract(orderEntity.getService()));
        order.setUser(EnhancedMessageFactory.extract(orderEntity.getUser()));

        order.setStatus(orderEntity.getStatus());
        order.setNumber(orderEntity.getNumber());
        return order;
    }

    public static OrderMessage compact(OrderEntity orderEntity) {
        return new OrderMessage(create(orderEntity));
    }

    public static OrderMessage compact(String recipientUserName, OrderEntity orderEntity) {
        return new OrderMessage(recipientUserName, create(orderEntity));
    }


}
