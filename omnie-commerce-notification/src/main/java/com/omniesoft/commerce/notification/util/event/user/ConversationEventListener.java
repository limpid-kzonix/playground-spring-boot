package com.omniesoft.commerce.notification.util.event.user;

import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.user.events.OnConversationUserNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("userConversationEventListener")
public class ConversationEventListener extends UserSender implements
        ApplicationListener<OnConversationUserNotifyEvent> {


    public ConversationEventListener(
            StompNotificationService notificationService) {

        super(notificationService);
    }

    @Override
    @EventListener
    public void onApplicationEvent(OnConversationUserNotifyEvent event) {

        send(event.getLookupSource(), "/topic/message/user");
    }
}
