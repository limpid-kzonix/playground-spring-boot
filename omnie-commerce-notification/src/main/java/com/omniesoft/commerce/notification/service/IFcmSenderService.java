package com.omniesoft.commerce.notification.service;

import com.omniesoft.commerce.common.notification.order.OrderNotifEvent;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.util.Set;

public interface IFcmSenderService {
    void orderNotif(Set<UserEntity> admins, OrderNotifEvent event);

    void orderNotif(UserEntity user, OrderNotifEvent event);
}
