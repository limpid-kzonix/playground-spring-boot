package com.omniesoft.commerce.notification.util.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener implements ApplicationListener<UserNotifyEvent> {

    @Override
    @EventListener
    public void onApplicationEvent(UserNotifyEvent event) {


    }


}
