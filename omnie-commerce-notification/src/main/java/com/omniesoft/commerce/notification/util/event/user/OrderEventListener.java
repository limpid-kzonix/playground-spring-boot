package com.omniesoft.commerce.notification.util.event.user;

import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.user.events.OnOrderUserNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("userOrderEventListener")
public class OrderEventListener extends UserSender implements ApplicationListener<OnOrderUserNotifyEvent> {


    public OrderEventListener(
            StompNotificationService notificationService) {

        super(notificationService);
    }

    @Override
    @EventListener
    public void onApplicationEvent(OnOrderUserNotifyEvent event) {
        send(event.getLookupSource(), "/topic/order/user");
    }
}
