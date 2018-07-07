package com.omniesoft.commerce.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import com.omniesoft.commerce.common.Constants;
import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.OrderNotifEvent;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
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

    @Override
    public void orderNotif(UserEntity user, OrderNotifEvent event) {
        user.getoAuth().stream()
                .filter(this::isFcmToken)
                .forEach(t -> sendFcmMessage(t.getProfileId(), event));
    }

    private void sendFcmMessage(String token, OrderNotifEvent event) {
        try {
            Message message = buildMessage(token, event);
            String response = FirebaseMessaging.getInstance().send(message);
            log.debug("Successfully sent message: " + response);
        } catch (JsonProcessingException e) {
            log.error("Json converting failed", e);
        } catch (FirebaseMessagingException e) {
            log.error("Sending FCM message failed", e);
        }
    }

    private Message buildMessage(String token, OrderNotifEvent event) throws JsonProcessingException {
        String title = "Omnie Commerce";
        String body = ((NotifMessage<OrderNotifPl>) event.getSource()).getContent().getOrgName();

        WebpushConfig webpushConfig = WebpushConfig.builder()
                .setNotification(new WebpushNotification(title, body))
                .build();

        Notification notification = new Notification(title, body);

        return Message.builder()
                .setNotification(notification)
                .setWebpushConfig(webpushConfig)
                .putData(Constants.FCM.PAYLOAD_KEY, mapper.writeValueAsString(event.getSource()))
                .putData(Constants.FCM.TARGET_KEY, event.getScope().getTarget().toString())
                .setToken(token)
                .build();
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

}
