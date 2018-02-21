package com.omniesoft.commerce.notification.util.event.admin;

import com.omniesoft.commerce.notification.models.service.RecipientService;
import com.omniesoft.commerce.notification.models.service.StompNotificationService;
import com.omniesoft.commerce.notification.util.event.admin.events.OnReviewAdminNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("adminReviewEventListener")
public class ReviewEventListener extends AdminSender implements ApplicationListener<OnReviewAdminNotifyEvent> {


    public ReviewEventListener(StompNotificationService notificationService,
                               RecipientService recipientService) {

        super(notificationService, recipientService);
    }

    @Override
    @EventListener
    public void onApplicationEvent(OnReviewAdminNotifyEvent event) {

        send(event.getLookupSource(), "/topic/review/admin");

    }


}
