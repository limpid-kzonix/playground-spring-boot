package com.omniesoft.commerce.common.notification.order;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.common.notification.scope.EventScope;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OrderNotifEvent extends ApplicationEvent {

    @Getter
    private EventScope scope;

    public OrderNotifEvent(NotifMessage<OrderNotifPl> source, EventScope scope) {
        super(source);
        this.scope = scope;
    }
}
