package com.omniesoft.commerce.user.service.conversation.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.entity.conversation.MessageEntity;
import com.omniesoft.commerce.persistence.entity.enums.MessageSender;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import com.omniesoft.commerce.persistence.repository.conversation.ConversationRepository;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepository;
import com.omniesoft.commerce.user.controller.conversation.payload.MessagePayload;
import com.omniesoft.commerce.user.service.conversation.MessageService;
import com.omniesoft.commerce.user.service.util.event.conversation.events.OnMessagePostEvent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public Page<MessageSummary> findMessagesByConversation(UUID conversationId, String search, Pageable pageable, UserEntity userEntity) {
        if (conversationRepository.findByUserAndId(userEntity, conversationId) == null) {
            throw new UsefulException();
        }
        return messageRepository.getMessagesPage(conversationId, search.toLowerCase(), pageable);
    }

    @Override
    public MessagePayload postMessageToConversation(UUID conversationId, String messagePayload,
                                                    UserEntity userEntity) {

        ConversationEntity one = checkConversationEntity(conversationId);

        MessageEntity messageEntity = prepareMessage(messagePayload, userEntity, one);
        MessageEntity save = messageRepository.save(messageEntity);
        publishMessageEvent(messageEntity);
        return modelMapper.map(save, MessagePayload.class);


    }

    private void publishMessageEvent(MessageEntity messageEntity) {
        applicationEventPublisher.publishEvent(new OnMessagePostEvent(messageEntity));
    }

    private ConversationEntity checkConversationEntity(UUID conversationId) {

        ConversationEntity one = conversationRepository.findOne(conversationId);

        if (one == null) {
            throw new UsefulException("Conversation with id = [" + conversationId + "] does not exist");
        }
        return one;
    }

    private MessageEntity prepareMessage(String message, UserEntity userEntity, ConversationEntity one) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setConversation(one);
        messageEntity.setMessage(message);
        messageEntity.setSender(userEntity);
        messageEntity.setReadUserStatus(true);
        messageEntity.setReadOrgStatus(false);
        messageEntity.setSentBy(MessageSender.USER);
        return messageEntity;
    }

    @Override
    public Page<MessageSummary> findPreviousMessages(UUID messageId, UUID conversationId,
                                                     UserEntity userEntity, Pageable pageable) {

        checkConversationEntity(conversationId);
        MessageEntity messageEntity = getMessageEntity(messageId);
        return messageRepository.findPreviousMessage(conversationId, messageEntity.getCreateTime(), pageable);
    }

    @Override
    public Page<MessageSummary> findNextMessages(UUID messageId, UUID conversationId,
                                                 UserEntity userEntity, Pageable pageable) {
        checkConversationEntity(conversationId);
        MessageEntity byId = getMessageEntity(messageId);
        return messageRepository.findNextMessage(conversationId, byId.getCreateTime(), pageable);
    }

    private MessageEntity getMessageEntity(UUID messageId) {
        MessageEntity byId = messageRepository.findById(messageId);
        if (byId == null) {
            throw new UsefulException("Message with id [" + messageId + "] does not exist", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return byId;
    }

    @Override
    public void readMessagesInConversation(UUID conversationId, UserEntity userEntity) {
        messageRepository.readMessages(conversationId, userEntity);
    }
}
