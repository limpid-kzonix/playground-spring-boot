package com.omniesoft.commerce.notification.util.event.admin;

import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import com.omniesoft.commerce.notification.models.service.RecipientService;
import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AdminSender {

    private StompNotificationService notificationService;
    private RecipientService recipientService;

    public void send(NotificationMessage lookupSource, String destination) {
        if (lookupSource == null) return;
        List<UserEntity> recipientByOrganizationId = recipientService
                .findRecipientByOrganizationId(lookupSource.getOrganizationId());

        recipientByOrganizationId.parallelStream().forEach(
                userEntity -> notificationService
                        .sendToUser(userEntity.getLogin(), destination, lookupSource));

    }

}
