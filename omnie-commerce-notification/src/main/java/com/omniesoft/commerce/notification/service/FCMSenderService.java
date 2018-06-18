package com.omniesoft.commerce.notification.service;

import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMSenderService implements IFCMSenderService {
    @Override
    public void orderNotif(Set<UserEntity> admins, OrderNotifEvent event) {
        log.info("NOT IMPLEMENTED YET");
        admins.forEach(e -> log.info(e.getLogin()));
    }

    @Override
    public void orderNotif(UserEntity user, OrderNotifEvent event) {
        log.info("NOT IMPLEMENTED YET");
        log.info(user.getLogin());
    }
}
