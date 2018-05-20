package com.omniesoft.commerce.notification.util.event.user.events;

import com.omniesoft.commerce.common.ws.notification.payload.order.OrderMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnOrderUserNotifyEvent extends ApplicationEvent {

    @Getter
    private OrderMessage lookupSource;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnOrderUserNotifyEvent(OrderMessage source) {

        super(source);
        this.lookupSource = source;
    }
}
