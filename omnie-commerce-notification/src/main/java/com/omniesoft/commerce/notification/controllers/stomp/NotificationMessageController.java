package com.omniesoft.commerce.notification.controllers.stomp;

import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import com.omniesoft.commerce.common.ws.notification.payload.order.OrderMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
@Slf4j
@AllArgsConstructor
@Configuration
public class NotificationMessageController {

    private SimpMessageSendingOperations messageSendingOperations;

    @SubscribeMapping(value = "/messages/notifications")
    public NotificationMessage getNotifications(Principal principal) {
        return new OrderMessage(null);
    }

    @MessageMapping(value = "/messages")
    @SendToUser
    public NotificationMessage postMessages(NotificationMessage message, Principal principal) {
        log.info("Handled message:" + message.toString());
        return new NotificationMessage() {
            @Override
            public String getStompUserName() {
                return "undefined";
            }

            @Override
            public UUID getOrganizationId() {
                return UUID.randomUUID();
            }
        };
    }


//    @Scheduled(fixedDelay = 20000)
//    public void send() {
//        messageSendingOperations
//                .convertAndSend("/topic/messages/notifications", new OrderMessage(null, null));
//
//        messageSendingOperations
//                .convertAndSendToUser("alexander", "/topic/messages/notifications", new OrderMessage(null, null));
//    }
}
