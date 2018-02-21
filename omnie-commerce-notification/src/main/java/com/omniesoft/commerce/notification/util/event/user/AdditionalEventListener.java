package com.omniesoft.commerce.notification.util.event.user;

import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.user.events.OnUserNotifyEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service("userAdditionalEventListener")
@AllArgsConstructor
public class AdditionalEventListener implements ApplicationListener<OnUserNotifyEvent> {

    private StompNotificationService notificationService;

    @Override
    public void onApplicationEvent(OnUserNotifyEvent event) {

    }
}
