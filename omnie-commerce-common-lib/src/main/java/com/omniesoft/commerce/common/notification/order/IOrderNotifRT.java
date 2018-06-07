package com.omniesoft.commerce.common.notification.order;

import com.omniesoft.commerce.persistence.entity.order.OrderEntity;

public interface IOrderNotifRT {

    void newOrder(OrderEntity order);

    void confirmOrder(OrderEntity order);

    void changedOrder(OrderEntity order);

    void doneOrder(OrderEntity order);

    void cancelOrder(OrderEntity order);

    void failOrder(OrderEntity failedOrder);
}
