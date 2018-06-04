package com.omniesoft.commerce.notification.util.event.user;

import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.user.events.OnReviewUserNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewEventListener extends UserSender implements ApplicationListener<OnReviewUserNotifyEvent> {


    public ReviewEventListener(StompNotificationService notificationService) {

        super(notificationService);
    }

    @Override
    @EventListener
    public void onApplicationEvent(OnReviewUserNotifyEvent event) {

//        send(event.getLookupSource(), "/topic/review/user");

    }


}
