package com.omniesoft.commerce.owner.service.conversation.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.owner.service.conversation.ConversationService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.conversation.ConversationRepository;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;


    @Override
    public Page<ConversationSummary> findOrganizationConversations(UUID organization, String searchPattern,
                                                                   UserEntity userEntity, Pageable pageable) {
        return conversationRepository.findConversationsInfoWithUser(organization, searchPattern, pageable);
    }

    @Override
    public UUID createConversation(UUID org, UUID conversationUserId, UserEntity userEntity) {

        UserEntity forConversation = getUserForConversation(conversationUserId);
        OrganizationEntity one = getOrganizationForConversation(org);
        ConversationEntity conversationEntity = prepareConversationEntity(forConversation, one);
        return conversationRepository.save(conversationEntity).getId();
    }

    @Override
    public MessageSummary fetchLastMessageInConversation(UUID org, UUID conversationId, UserEntity userEntity) {
        return messageRepository.findTopByConversationIdOrderByCreateTimeDesc(conversationId);
    }

    private ConversationEntity prepareConversationEntity(UserEntity forConversation, OrganizationEntity one) {

        ConversationEntity conversationEntity = new ConversationEntity();
        conversationEntity.setOrganization(one);
        conversationEntity.setUser(forConversation);
        conversationEntity.setCreateTime(LocalDateTime.now());
        return conversationEntity;
    }

    private OrganizationEntity getOrganizationForConversation(UUID org) {

        OrganizationEntity one = organizationRepository.findOne(org);
        if (one == null) {
            throw new UsefulException(org.toString(), InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return one;
    }

    private UserEntity getUserForConversation(UUID conversationUserId) {

        UserEntity forConversation = userRepository.findOne(conversationUserId);
        if (forConversation == null) {
            throw new UsefulException(conversationUserId.toString(),
                    OwnerModuleErrorCodes.CLIENT_PROFILE_NOT_EXIST);
        }
        return forConversation;
    }
}
