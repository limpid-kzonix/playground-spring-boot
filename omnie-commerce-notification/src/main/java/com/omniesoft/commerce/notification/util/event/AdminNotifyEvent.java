package com.omniesoft.commerce.notification.util.event;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.notification.event.admin.events.AdminEventScope;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class AdminNotifyEvent extends ApplicationEvent {
    @Getter
    private AdminEventScope scope;

    public AdminNotifyEvent(NotifMessage source, AdminEventScope scope) {
        super(source);
        this.scope = scope;
    }
}
