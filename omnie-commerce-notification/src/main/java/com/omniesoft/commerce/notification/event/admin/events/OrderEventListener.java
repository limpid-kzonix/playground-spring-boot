package com.omniesoft.commerce.notification.event.admin.events;

import com.omniesoft.commerce.common.Constants;
import com.omniesoft.commerce.notification.ISearchAdminService;
import com.omniesoft.commerce.notification.util.event.UserNotifyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderEventListener implements ApplicationListener<UserNotifyEvent> {
    private final SimpMessageSendingOperations ws;
    private final ISearchAdminService findAdmin;

    @Override
    @EventListener
    public void onApplicationEvent(UserNotifyEvent event) {
        String owner = findAdmin.getOwner(event.getScope().getOrganizationReceiver());
        ws.convertAndSendToUser(owner, Constants.WS.Owner.NOTIFICATION, event.getSource());

    }
}
