package com.omniesoft.commerce.notification.util.event.admin;

import com.omniesoft.commerce.notification.models.service.RecipientService;
import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.user.events.OnConversationUserNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("adminConversationEventListener")
public class ConversationEventListener extends AdminSender implements ApplicationListener<OnConversationUserNotifyEvent> {


    public ConversationEventListener(
            StompNotificationService notificationService,
            RecipientService recipientService) {

        super(notificationService, recipientService);
    }

    @Override
    @EventListener
    public void onApplicationEvent(OnConversationUserNotifyEvent event) {

        send(event.getLookupSource(), "/topic/message/admin");
    }
}
