package com.omniesoft.commerce.notification.controller.receiver;

import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;
import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import com.omniesoft.commerce.notification.util.event.user.events.OnConversationUserNotifyEvent;
import com.omniesoft.commerce.notification.util.event.user.events.OnReviewUserNotifyEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class NotifUserController extends AbstractNotifController {

    private final ApplicationEventPublisher eventPublisher;

//    @PostMapping(path = "/user/order")
//    public void handleOrderNotification(@RequestBody NotifMessage<OrderNotifPl> orderMessage) {
//
//    }

    @PostMapping(path = "/user/conversation")
    public void handleConversationNotification(
            @Valid @RequestBody ConversationMessage conversationMessage
    ) {

        eventPublisher.publishEvent(new OnConversationUserNotifyEvent(conversationMessage));
    }

    @PostMapping(path = "/user/review")
    public void handleReviewNotification(
            @Valid @RequestBody ReviewMessage reviewMessage
    ) {

        eventPublisher.publishEvent(new OnReviewUserNotifyEvent(reviewMessage));
    }

    @PostMapping(path = "/user/general")
    public void handleGeneralNotification() {

    }

}
