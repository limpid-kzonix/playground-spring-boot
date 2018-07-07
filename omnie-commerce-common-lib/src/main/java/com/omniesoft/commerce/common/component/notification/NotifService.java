package com.omniesoft.commerce.common.component.notification;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.OrderNotifEvent;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.notification.NotifEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.repository.notification.NotifRepository;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotifService implements INotifService {
    private final NotifRepository notifRepository;
    private final OrderRepository orderRepository;


    @Override
    public void save(Set<UserEntity> admins, OrderNotifEvent event) {
        List<NotifEntity> notifEntities = admins.stream()
                .map(a -> map(a, event))
                .collect(Collectors.toList());
        notifRepository.save(notifEntities);
    }

    @Override
    public void save(UserEntity user, OrderNotifEvent event) {
        NotifEntity entity = map(user, event);
        notifRepository.save(entity);
    }

    @Override
    public Page<NotifMessage<OrderNotifPl>> findAdminNotif(UserEntity user, Pageable pageable) {
        Page<NotifEntity> page = notifRepository.findAdminNotif(user.getId(), pageable);
        List<NotifMessage<OrderNotifPl>> collect = page.getContent()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
        return new PageImpl<>(collect, new PageRequest(page.getNumber(), page.getSize()), page.getTotalElements());
    }

    private NotifMessage<OrderNotifPl> map(NotifEntity notif) {

        NotifMessage<OrderNotifPl> message = new NotifMessage<>();
        message.setId(notif.getId());
        message.setType(notif.getType());
        message.setContent(OrderNotifPl.map(getOrder(notif)));

        return message;
    }

    private OrderEntity getOrder(NotifEntity notif) {
        // TODO: 07.07.18: add logic if notification will be related not only for orders
        return orderRepository.findOne(notif.getItem());
    }

    private NotifEntity map(UserEntity user, OrderNotifEvent event) {
        NotifEntity notifEntity = new NotifEntity();
        notifEntity.setReceiver(user);
        // TODO: 07.07.18: add logic if notification will be related not only for orders
        NotifMessage<OrderNotifPl> source = (NotifMessage<OrderNotifPl>) event.getSource();
        notifEntity.setItem(source.getContent().getOrderId());
        notifEntity.setTarget(event.getScope().getTarget());
        notifEntity.setType(source.getType());
        return notifEntity;
    }
}
