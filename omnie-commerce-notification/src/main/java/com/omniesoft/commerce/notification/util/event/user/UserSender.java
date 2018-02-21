package com.omniesoft.commerce.notification.util.event.user;

import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSender {

    private StompNotificationService notificationService;

    public void send(NotificationMessage lookupSource, String destination) {
        if (lookupSource == null) return;
        notificationService.sendToUser(lookupSource.getStompUserName(), destination, lookupSource);
    }

}
