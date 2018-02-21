package com.omniesoft.commerce.notification.models.service.impl;

import com.omniesoft.commerce.notification.models.service.NotificationSubscribeService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepository;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminNotification")
@AllArgsConstructor
public class NotificationAdminSubscribeServiceImpl implements NotificationSubscribeService {

    private OrganizationRepository organizationRepository;

    private UserRepository userRepository;

    private MessageRepository messageRepository;

    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void notifyAboutUnreadMessages(String userIdentifier) {

        UserEntity userEntity = userRepository.findByLogin(userIdentifier);
        List<OrganizationEntity> byOwnerOrAdmin = organizationRepository.findByOwnerOrAdmin(userEntity);
        byOwnerOrAdmin.forEach(
                organizationEntity -> messageRepository.getUnreadMessagesForOrganization(organizationEntity.getId()));
        //TODO: use sending over stomp

    }

    @Override
    public void notifyAboutOrder(String userIdentifier) {

        UserEntity userEntity = userRepository.findByLogin(userIdentifier);
        List<OrganizationEntity> byOwnerOrAdmin = organizationRepository.findByOwnerOrAdmin(userEntity);
        byOwnerOrAdmin.forEach(organizationEntity -> orderRepository
                .findAllByServiceOrganizationIdAndStatus(organizationEntity.getId(), OrderStatus
                        .PENDING_FOR_ADMIN));
        //TODO: use sending over stomp
    }
}
