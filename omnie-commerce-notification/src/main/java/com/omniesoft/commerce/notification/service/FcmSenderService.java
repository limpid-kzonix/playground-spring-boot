package com.omniesoft.commerce.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserOAuthEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmSenderService implements IFcmSenderService {
    private final ObjectMapper mapper;

    @Override
    public void orderNotif(Set<UserEntity> admins, OrderNotifEvent event) {

        Set<String> tokens = getFcmTokens(admins);

        tokens.forEach(t -> sendFcmMessage(t, event));

    }

    private void sendFcmMessage(String token, OrderNotifEvent event) {
        try {
            Message message = Message.builder()
                    .setNotification(new Notification("Order", mapper.writeValueAsString(event)))
                    .setToken(token)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.debug("Successfully sent message: " + response);
        } catch (JsonProcessingException e) {
            log.error("Json converting failed", e);
        } catch (FirebaseMessagingException e) {
            log.error("Sending FCM message failed", e);
        }
    }

    private Set<String> getFcmTokens(Set<UserEntity> admins) {
        return admins.stream()
                .flatMap(a -> a.getoAuth().stream()
                        .filter(oa -> isFcmToken(oa))
                        .map(UserOAuthEntity::getProfileId))
                .collect(Collectors.toSet());
    }

    private boolean isFcmToken(UserOAuthEntity oa) {
        return OAuthClient.FCM.equals(oa.getOauthClient());
    }

    @Override
    public void orderNotif(UserEntity user, OrderNotifEvent event) {
        user.getoAuth().stream()
                .filter(this::isFcmToken)
                .forEach(t -> sendFcmMessage(t.getProfileId(), event));

    }
}
