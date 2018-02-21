package com.omniesoft.commerce.notification.models.service.impl;

import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StompNotificationServiceImpl implements StompNotificationService {

    private SimpMessageSendingOperations stomp;

    @Override
    public void sendToUser(String userIdentifier, String subscribeDestination,
                           NotificationMessage notificationMessage) {
        stomp.convertAndSendToUser(userIdentifier, subscribeDestination, new Object());
    }
}
