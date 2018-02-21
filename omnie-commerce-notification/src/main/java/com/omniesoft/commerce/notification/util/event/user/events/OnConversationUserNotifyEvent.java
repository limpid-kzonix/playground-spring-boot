package com.omniesoft.commerce.notification.util.event.user.events;

import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnConversationUserNotifyEvent extends ApplicationEvent {

    @Getter
    private ConversationMessage lookupSource;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnConversationUserNotifyEvent(ConversationMessage source) {
        super(source);
        this.lookupSource = source;
    }
}
