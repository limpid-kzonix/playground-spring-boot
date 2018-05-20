package com.omniesoft.commerce.notification.config.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StompChannelInterceptorAdapterImpl implements StompChannelInterceptorAdapter {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        log.debug("User with session-id [{}] : {} preSend {} ",
//                accessor.getSessionId(),
//                accessor.getDestination(),
//                message.getPayload());
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        log.debug("User with session-id [{}] : {} postSend {} ",
//                accessor.getSessionId(),
//                accessor.getDestination(),
//                message.getPayload());

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.debug("User with session-id [{}] : {} afterSendCompletion {} with status : Complete",
                accessor.getSessionId(),
                accessor.getDestination(),
                message.getPayload());
    }

    @Override
    public boolean preReceive(MessageChannel channel) {

//        log.debug("Expecting message from inbound chanel");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {

//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        log.debug("User with session-id [{}] : {} postReceive {} with status : Complete",
//                accessor.getSessionId(),
//                accessor.getDestination(),
//                message.getPayload());
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.debug("User with session-id [{}] : {} afterReceiveCompletion {} with status : Complete",
                accessor.getSessionId(),
                accessor.getDestination(),
                message.getPayload());
    }
}
