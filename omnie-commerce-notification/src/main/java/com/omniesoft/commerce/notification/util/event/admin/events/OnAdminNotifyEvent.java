package com.omniesoft.commerce.notification.util.event.admin.events;

import org.springframework.context.ApplicationEvent;

public class OnAdminNotifyEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnAdminNotifyEvent(Object source) {

        super(source);
    }
}
