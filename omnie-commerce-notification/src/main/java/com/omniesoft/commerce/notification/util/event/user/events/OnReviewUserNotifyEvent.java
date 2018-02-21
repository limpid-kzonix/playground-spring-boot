package com.omniesoft.commerce.notification.util.event.user.events;

import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnReviewUserNotifyEvent extends ApplicationEvent {

    @Getter
    private ReviewMessage lookupSource;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnReviewUserNotifyEvent(ReviewMessage source) {

        super(source);
        this.lookupSource = source;
    }
}
