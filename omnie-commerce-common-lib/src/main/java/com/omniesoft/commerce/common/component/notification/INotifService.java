package com.omniesoft.commerce.common.component.notification;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.OrderNotifEvent;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface INotifService {

    void save(Set<UserEntity> admins, OrderNotifEvent event);

    void save(UserEntity user, OrderNotifEvent event);

    Page<NotifMessage<OrderNotifPl>> findAdminNotif(UserEntity user, Pageable pageable);
}
