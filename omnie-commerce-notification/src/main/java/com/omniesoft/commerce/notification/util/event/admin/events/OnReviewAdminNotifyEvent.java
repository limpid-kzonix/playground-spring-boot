package com.omniesoft.commerce.notification.util.event.admin.events;

import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnReviewAdminNotifyEvent extends ApplicationEvent {

    @Getter
    private ReviewMessage lookupSource;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnReviewAdminNotifyEvent(ReviewMessage source) {

        super(source);
        this.lookupSource = source;
    }
}
