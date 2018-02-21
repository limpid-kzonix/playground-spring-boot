package com.omniesoft.commerce.notification.config;

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

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("User with session-id [{}] sending message {} ", accessor.getSessionId(),
                message.getPayload().toString());
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("User with session-id [{}] already sent message {} ", accessor.getSessionId(), message.getPayload());

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("User with session-id [{}] already sent message {} with status : Complete", accessor.getSessionId(),
                message.getPayload());
    }

    @Override
    public boolean preReceive(MessageChannel channel) {

        log.info("Expecting message from inbound chanel");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("User with session-id [{}] already received message {} with status : Complete",
                accessor.getSessionId(),
                message.getPayload());
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("User with session-id [{}] already received message {} with status : Complete", accessor
                        .getSessionId(),
                message.getPayload());
    }
}
