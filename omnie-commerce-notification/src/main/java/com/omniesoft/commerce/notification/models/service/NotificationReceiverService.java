package com.omniesoft.commerce.notification.models.service;


import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;

public interface NotificationReceiverService {

    void receive(ConversationMessage notificationMessage);


//    void receive(OrderMessage notificationMessage);
}
