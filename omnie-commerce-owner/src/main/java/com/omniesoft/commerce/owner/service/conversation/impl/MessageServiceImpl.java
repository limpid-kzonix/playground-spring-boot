package com.omniesoft.commerce.owner.service.conversation.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.owner.controller.conversation.payload.MessagePayload;
import com.omniesoft.commerce.owner.service.conversation.MessageService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.entity.conversation.MessageEntity;
import com.omniesoft.commerce.persistence.entity.enums.MessageSender;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import com.omniesoft.commerce.persistence.repository.conversation.ConversationRepository;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ConversationRepository conversationRepository;

    private final ModelMapper modelMapper;

    private final OrganizationRepository organizationRepository;


    @Override
    public Page<MessageSummary> findMessageInConversation(String searchPattern, UUID conversationId, UUID organization,
                                                          UserEntity userEntity, Pageable pageable) {
        return messageRepository.getMessagesPage(conversationId, searchPattern.toLowerCase(), pageable);
    }

    @Override
    public MessagePayload postMessageToConversation(UUID organization, UUID conversationId, String
            messagePayload,
                                                    UserEntity userEntity) {


        ConversationEntity one = getConversationEntity(organization, conversationId);

        MessageEntity messageEntity = prepareMessage(messagePayload, userEntity, one);
        MessageEntity saved = messageRepository.save(messageEntity);
        return modelMapper.map(saved, MessagePayload.class);


    }

    private ConversationEntity getConversationEntity(UUID organization, UUID conversationId) {

        ConversationEntity one = conversationRepository.findByOrganizationIdAndId(organization, conversationId);
        if (one == null) {
            throw new UsefulException("Conversation with id [" + conversationId + "] does not exist",
                    InternalErrorCodes
                            .RESOURCE_NOT_FOUND);
        }
        return one;
    }

    private MessageEntity prepareMessage(
            String messagePayload, UserEntity userEntity,
            ConversationEntity one) {

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setConversation(one);
        messageEntity.setMessage(messagePayload);
        messageEntity.setSender(userEntity);

        //set readStatus for organization 'true'
        messageEntity.setReadOrgStatus(true);
        //set readStatus for user 'false', user need to read this message
        messageEntity.setReadUserStatus(false);
        messageEntity.setSentBy(MessageSender.ORGANIZATION);
        return messageEntity;
    }


    @Override
    @Transactional
    public Page<MessageSummary> findPreviousMessages(UUID organization, UUID messageId, UUID conversationId,
                                                     UserEntity userEntity, Pageable pageable) {
        checkConversationEntity(conversationId);
        MessageEntity messageEntity = getMessageEntity(messageId);
        return messageRepository.findPreviousMessage(conversationId, messageEntity.getCreateTime(), pageable);
    }

    @Override
    @Transactional
    public Page<MessageSummary> findNextMessages(UUID organization, UUID messageId, UUID conversationId,
                                                 UserEntity userEntity, Pageable pageable) {
        checkConversationEntity(conversationId);
        MessageEntity messageEntity = getMessageEntity(messageId);
        return messageRepository.findNextMessage(conversationId, messageEntity.getCreateTime(), pageable);
    }

    @Override
    public void readMessage(UUID organizationId, UUID messageId, UUID conversationId, UserEntity userEntity) {
        OrganizationEntity organizationEntity = getOrganizationEntity(organizationId);
        messageRepository.readMessages(conversationId, organizationEntity, userEntity);
    }

    private OrganizationEntity getOrganizationEntity(UUID organizationId) {
        OrganizationEntity one = organizationRepository.findOne(organizationId);
        if (one == null) {
            throw new UsefulException("Organization with such id [" + organizationId + "] does not exist", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return one;
    }

    private MessageEntity getMessageEntity(UUID messageId) {
        MessageEntity byId = messageRepository.findById(messageId);
        if (byId == null && byId.getCreateTime() == null) {
            throw new UsefulException("Message with id [" + messageId + "] does not exist", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return byId;
    }

    private ConversationEntity checkConversationEntity(UUID conversationId) {

        ConversationEntity one = conversationRepository.findOne(conversationId);

        if (one == null) {
            throw new UsefulException("Conversation with id = [" + conversationId + "] does not exist");
        }
        return one;
    }
}
