package com.omniesoft.commerce.notification.event.admin.events;

import com.omniesoft.commerce.common.Constants;
import com.omniesoft.commerce.notification.ISearchAdminService;
import com.omniesoft.commerce.notification.event.UserEventScope;
import com.omniesoft.commerce.notification.util.event.admin.events.OrderNotifEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderEventListener implements ApplicationListener<OrderNotifEvent> {
    private final SimpMessageSendingOperations ws;
    private final ISearchAdminService findAdmin;

    @Override
    @EventListener
    public void onApplicationEvent(OrderNotifEvent event) {

        if (event.getScope() instanceof UserEventScope) {
            UserEventScope scope = (UserEventScope) event.getScope();
            String owner = findAdmin.getOwner(scope.getOrganizationReceiver());
            ws.convertAndSendToUser(owner, Constants.WS.Owner.NOTIFICATION, event.getSource());
        } else {
            AdminEventScope scope = (AdminEventScope) event.getScope();
            ws.convertAndSendToUser(scope.getUserReceiver(), Constants.WS.User.NOTIFICATION, event.getSource());
        }

    }
}
