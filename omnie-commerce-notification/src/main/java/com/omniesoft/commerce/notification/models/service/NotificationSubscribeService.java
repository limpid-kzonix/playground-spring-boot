package com.omniesoft.commerce.notification.models.service;

public interface NotificationSubscribeService {

    void notifyAboutUnreadMessages(String userIdentifier);

    void notifyAboutOrder(String userIdentifier);

}
