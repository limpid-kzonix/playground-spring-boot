package com.omniesoft.commerce.notification.models.service.impl;

import com.omniesoft.commerce.notification.models.service.NotificationSubscribeService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepository;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userNotification")
@AllArgsConstructor
public class NotificationUserSubscribeServiceImpl implements NotificationSubscribeService {

    private MessageRepository messageRepository;

    private OrderRepository orderRepository;

    private UserRepository userRepository;


    @Override
    public void notifyAboutUnreadMessages(String userIdentifier) {

        messageRepository.getUnreadMessagesForUser(userIdentifier);
    }

    @Override
    public void notifyAboutOrder(String userIdentifier) {

        UserEntity byLogin = userRepository.findByLogin(userIdentifier);
        orderRepository.findAllByUserIdAndStatus(byLogin.getId(), OrderStatus.PENDING_FOR_USER);
    }
}
