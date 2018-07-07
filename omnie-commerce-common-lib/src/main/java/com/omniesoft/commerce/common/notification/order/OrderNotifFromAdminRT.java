package com.omniesoft.commerce.common.notification.order;

import com.omniesoft.commerce.common.Constants;
import com.omniesoft.commerce.common.component.url.UrlBuilder;
import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.common.rest.ITokenRT;
import com.omniesoft.commerce.persistence.entity.enums.NotifType;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderNotifFromAdminRT implements IOrderNotifRT {
    private final ITokenRT rt;
    private final UrlBuilder urls;

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void newOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = createOrderNotif(order, NotifType.ORDER_NEW);
            rt.sentHttpRequest(message, urls.orderNotifForUser(order.getUser().getLogin()));
        }
    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void confirmOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = createOrderNotif(order, NotifType.ORDER_CONFIRM);
            rt.sentHttpRequest(message, urls.orderNotifForUser(order.getUser().getLogin()));
        }
    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void changedOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = createOrderNotif(order, NotifType.ORDER_CHANGE);
            rt.sentHttpRequest(message, urls.orderNotifForUser(order.getUser().getLogin()));
        }
    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void doneOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = createOrderNotif(order, NotifType.ORDER_DONE);
            rt.sentHttpRequest(message, urls.orderNotifForUser(order.getUser().getLogin()));
        }
    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void cancelOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = createOrderNotif(order, NotifType.ORDER_CANCEL);
            rt.sentHttpRequest(message, urls.orderNotifForUser(order.getUser().getLogin()));
        }
    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void failOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = createOrderNotif(order, NotifType.ORDER_FAIL);
            rt.sentHttpRequest(message, urls.orderNotifForUser(order.getUser().getLogin()));
        }
    }

    private NotifMessage<OrderNotifPl> createOrderNotif(OrderEntity order, NotifType orderConfirm) {
        NotifMessage<OrderNotifPl> message = new NotifMessage<>();
        message.setType(orderConfirm);
        message.setContent(OrderNotifPl.map(order));
        return message;
    }


}
