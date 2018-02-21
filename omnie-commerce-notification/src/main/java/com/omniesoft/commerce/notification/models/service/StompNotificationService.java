package com.omniesoft.commerce.notification.models.service;


import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;

public interface StompNotificationService {

    void sendToUser(String userIdentifier, String subscribeDestination, NotificationMessage notificationMessage);

}
