package com.omniesoft.commerce.notification.controller.receiver;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;
import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.notification.event.scope.AdminEventScope;
import com.omniesoft.commerce.notification.util.event.user.events.OnConversationUserNotifyEvent;
import com.omniesoft.commerce.notification.util.event.user.events.OnReviewUserNotifyEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static com.omniesoft.commerce.common.Constants.WS.User.NOTIFICATION;

@RestController
@AllArgsConstructor
public class NotifUserController extends AbstractNotifController {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(path = "/user/{user-name}/order")
    public void handleOrderNotification(Principal emitter,
                                        @PathVariable("user-name") String userName,
                                        @RequestBody NotifMessage<OrderNotifPl> message) {
        AdminEventScope scope = AdminEventScope.of(emitter, NOTIFICATION, userName);
        eventPublisher.publishEvent(new OrderNotifEvent(message, scope));

    }

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
