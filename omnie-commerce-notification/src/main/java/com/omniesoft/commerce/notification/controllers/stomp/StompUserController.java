package com.omniesoft.commerce.notification.controllers.stomp;

import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import com.omniesoft.commerce.notification.models.service.NotificationSubscribeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

import static com.omniesoft.commerce.notification.controllers.StubMessage.getNotificationMessages;

@Controller
public class StompUserController {

    private NotificationSubscribeService notificationSubscribeService;

    public StompUserController(@Qualifier("userNotification") NotificationSubscribeService notificationSubscribeService) {

        this.notificationSubscribeService = notificationSubscribeService;
    }

    @SubscribeMapping(value = "/order/user")
    public List<NotificationMessage> getOrganizationNotificationsAboutOrder(Principal principal) {

        return getNotificationMessages();
    }

    @SubscribeMapping(value = "/messages/user")
    public List<NotificationMessage> getOrganizationNotificationsAboutMessage(Principal principal) {

        return getNotificationMessages();
    }


    @SubscribeMapping(value = "/review/user")
    public List<NotificationMessage> getOrganizationNotificationsAboutReview(Principal principal) {

        return getNotificationMessages();
    }
}
