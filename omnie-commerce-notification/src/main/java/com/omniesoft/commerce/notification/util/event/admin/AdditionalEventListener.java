package com.omniesoft.commerce.notification.util.event.admin;

import com.omniesoft.commerce.notification.util.event.user.events.OnUserNotifyEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service("adminAdditionalEventListener")
@AllArgsConstructor
public class AdditionalEventListener implements ApplicationListener<OnUserNotifyEvent> {


    @Override
    public void onApplicationEvent(OnUserNotifyEvent event) {

    }
}
