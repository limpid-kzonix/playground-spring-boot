package com.omniesoft.commerce.notification.util.event.user.events;

import org.springframework.context.ApplicationEvent;

public class OnUserNotifyEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnUserNotifyEvent(Object source) {

        super(source);
    }
}
