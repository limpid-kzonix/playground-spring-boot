package com.omniesoft.commerce.notification.listener;

import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.notification.event.scope.AdminEventScope;
import com.omniesoft.commerce.notification.event.scope.UserEventScope;
import com.omniesoft.commerce.notification.service.ISearchAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventListener implements ApplicationListener<OrderNotifEvent> {
    private final SimpMessageSendingOperations ws;
    private final ISearchAdminService findAdmin;

    @Override
    @EventListener
    public void onApplicationEvent(OrderNotifEvent event) {
        log.debug("New event from: {}", event.getScope().getEventEmitter().getName());
        String userName;
        if (event.getScope() instanceof UserEventScope) {
            UserEventScope scope = (UserEventScope) event.getScope();
            userName = findAdmin.getOwner(scope.getOrganizationReceiver());
        } else {
            AdminEventScope scope = (AdminEventScope) event.getScope();
            userName = scope.getUserReceiver();
        }
        ws.convertAndSendToUser(userName, event.getScope().getDestination(), event.getSource());
    }
}
