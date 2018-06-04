package com.omniesoft.commerce.notification.util.event;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.notification.event.UserEventScope;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * user as target
 */
public class UserNotifyEvent extends ApplicationEvent {
    @Getter
    private UserEventScope scope;

    public UserNotifyEvent(NotifMessage source, UserEventScope scope) {
        super(source);
        this.scope = scope;
    }
}
