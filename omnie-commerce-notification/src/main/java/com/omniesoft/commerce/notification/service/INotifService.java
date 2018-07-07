package com.omniesoft.commerce.notification.service;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface INotifService {

    void save(Set<UserEntity> admins, OrderNotifEvent event);

    void save(UserEntity user, OrderNotifEvent event);

    Page<NotifMessage<OrderNotifPl>> find(UUID organizationId, UUID serviceId, UserEntity user, Pageable pageable);
}
