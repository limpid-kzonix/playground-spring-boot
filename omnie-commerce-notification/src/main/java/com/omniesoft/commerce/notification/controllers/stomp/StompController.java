package com.omniesoft.commerce.notification.controllers.stomp;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
@Slf4j
public class StompController {
    private final SimpMessagingTemplate ws;

    @SubscribeMapping("/user/notification")
    @SendToUser("/user/notification")
    public NotifMessage<OrderNotifPl> broadcast(Principal principal) {
        log.debug("SUBSCRIBED::::: {}", principal.getName());

        NotifMessage orderNotifpl = new NotifMessage();

        return orderNotifpl;
    }
}
