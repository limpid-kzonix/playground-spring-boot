package com.omniesoft.commerce.notification.util.event.admin;

import com.omniesoft.commerce.notification.models.service.RecipientService;
import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.user.events.OnOrderUserNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("adminOrderEventListener")
public class OrderEventListener extends AdminSender implements ApplicationListener<OnOrderUserNotifyEvent> {


    public OrderEventListener(
            StompNotificationService notificationService,
            RecipientService recipientService) {

        super(notificationService, recipientService);
    }

    @Override
    @EventListener
    public void onApplicationEvent(OnOrderUserNotifyEvent event) {
        send(event.getLookupSource(), "/topic/order/admin");
    }
}
