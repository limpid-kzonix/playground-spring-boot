package com.omniesoft.commerce.user.service.conversation;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import com.omniesoft.commerce.user.controller.conversation.payload.MessagePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService {

    Page<MessageSummary> findMessagesByConversation(UUID conversationId, String search, Pageable pageable, UserEntity userEntity);

    MessagePayload postMessageToConversation(UUID conversationId, String messagePayload, UserEntity userEntity);

    Page<MessageSummary> findPreviousMessages(UUID messageId, UUID conversationId, UserEntity
            userEntity, Pageable pageable);

    Page<MessageSummary> findNextMessages(UUID messageId, UUID conversationId, UserEntity
            userEntity, Pageable pageable);

    void readMessagesInConversation(UUID conversationId, UserEntity userEntity);
}
