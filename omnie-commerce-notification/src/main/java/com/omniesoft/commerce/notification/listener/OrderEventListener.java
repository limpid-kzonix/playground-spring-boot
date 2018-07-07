package com.omniesoft.commerce.notification.listener;

import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.notification.event.scope.AdminEventScope;
import com.omniesoft.commerce.notification.event.scope.UserEventScope;
import com.omniesoft.commerce.notification.service.IFcmSenderService;
import com.omniesoft.commerce.notification.service.INotifService;
import com.omniesoft.commerce.notification.service.IOnlineUsersCheckService;
import com.omniesoft.commerce.notification.service.ISearchUsersService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventListener implements ApplicationListener<OrderNotifEvent> {
    private final SimpMessageSendingOperations ws;
    private final ISearchUsersService search;
    private final IOnlineUsersCheckService onlineUsersCheck;
    private final IFcmSenderService fcm;
    private final INotifService notifSaving;

    @Override
    @EventListener
    public void onApplicationEvent(OrderNotifEvent event) {
        log.debug("New event from: {}", event.getScope().getEventEmitter().getName());
        log.debug("Event: {}", event.getSource());

        Set<UserEntity> admins;
        if (event.getScope() instanceof UserEventScope) {
            UserEventScope scope = (UserEventScope) event.getScope();
            admins = search.getAdminsAndOwner(scope.getOrganizationReceiver());
            Set<String> onlineAdmins = onlineUsersCheck.filterOffline(admins.stream().map(UserEntity::getLogin).collect(Collectors.toSet()));
            if (!isEmpty(onlineAdmins)) {
                broadcast(onlineAdmins, event);
                return;
            }
            fcm.orderNotif(admins, event);
        } else {
            AdminEventScope scope = (AdminEventScope) event.getScope();
            if (onlineUsersCheck.isOnline(scope.getUserReceiver())) {
                log.debug("Send event wia WS: {} \n {}", scope.getUserReceiver(), event);
                ws.convertAndSendToUser(scope.getUserReceiver(), event.getScope().getDestination(), event.getSource());
                return;
            }
            UserEntity users = search.getUser(scope.getUserReceiver());
            fcm.orderNotif(users, event);

        }

    }

    private void broadcast(Iterable<String> userNames, OrderNotifEvent event) {
        userNames.forEach(u -> {
                    log.debug("Broadcast event wia WS: {} \n {}", u, event);
                    ws.convertAndSendToUser(u, event.getScope().getDestination(), event.getSource());
                }
        );
    }
}
