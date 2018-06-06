package com.omniesoft.commerce.common.rest;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;

import java.net.URI;

public interface ITokenRT {
    void sentHttpRequest(NotifMessage<OrderNotifPl> message, URI uri);
}
