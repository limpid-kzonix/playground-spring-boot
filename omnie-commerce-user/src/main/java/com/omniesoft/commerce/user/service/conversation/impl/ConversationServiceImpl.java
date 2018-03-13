package com.omniesoft.commerce.user.service.conversation.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import com.omniesoft.commerce.persistence.repository.conversation.ConversationRepository;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.user.service.conversation.ConversationService;
import com.omniesoft.commerce.user.service.util.event.conversation.events.OnConversationCreateEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ConversationServiceImpl implements ConversationService {


    private final ConversationRepository conversationRepository;

    private final OrganizationRepository organizationRepository;

    private final MessageRepository messageRepository;

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public Page<ConversationSummary> findUsersConversations(String search, UserEntity userEntity, Pageable pageable) {
        return conversationRepository.findConversationsInfoWithOrganization(userEntity, search.toLowerCase(), pageable);
    }

    @Override
    public UUID createConversation(UUID org, UserEntity userEntity) {

        ConversationSummary byUserIdAndOrganizationId = conversationRepository.findByUserIdAndOrganizationId(userEntity.getId(), org);
        if (byUserIdAndOrganizationId != null) {
            return byUserIdAndOrganizationId.getId();
        }
        OrganizationEntity one = checkOrganizationEntity(org);
        ConversationEntity conversationEntity = new ConversationEntity();
        conversationEntity.setOrganization(one);
        conversationEntity.setUser(userEntity);


        ConversationEntity save = conversationRepository.save(conversationEntity);
        publishConversationEvent(conversationEntity);
        return save.getId();
    }

    private void publishConversationEvent(ConversationEntity conversationEntity) {
        applicationEventPublisher.publishEvent(new OnConversationCreateEvent(conversationEntity));
    }

    @Override
    public MessageSummary fetchLastMessageInConversation(UUID conversationId, UserEntity userEntity) {
        return messageRepository.findTopByConversationIdOrderByCreateTimeDesc(conversationId);
    }

    private OrganizationEntity checkOrganizationEntity(UUID org) {

        OrganizationEntity one = organizationRepository.findOne(org);
        if (one == null) {
            throw new UsefulException("Organization with id [" + org.toString() + "]");
        }
        return one;
    }


}
