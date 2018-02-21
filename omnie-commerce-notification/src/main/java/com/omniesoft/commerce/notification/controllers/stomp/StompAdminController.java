package com.omniesoft.commerce.notification.controllers.stomp;

import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

import static com.omniesoft.commerce.notification.controllers.StubMessage.getNotificationMessages;

@Controller
@AllArgsConstructor
public class StompAdminController {
    @SubscribeMapping(value = "/order/admin")
    public List<NotificationMessage> getClientNotificationsAboutOrder(Principal principal) {
        return getNotificationMessages();
    }

    @SubscribeMapping(value = "/messages/admin")
    public List<NotificationMessage> getClientNotificationsAboutMessage(Principal principal) {
        return getNotificationMessages();
    }

    @SubscribeMapping(value = "/review/admin")
    public List<NotificationMessage> getUnacceptedReview(Principal principal) {
        return getNotificationMessages();
    }
}
