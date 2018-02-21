package com.omniesoft.commerce.owner.service.conversation;

import com.omniesoft.commerce.owner.controller.conversation.payload.MessagePayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService {
    Page<MessageSummary> findMessageInConversation(
            String searchPattern,
            UUID conversationId,
            UUID organization,
            UserEntity userEntity,
            Pageable pageable
    );

    MessagePayload postMessageToConversation(UUID organiation, UUID conversationId, String messagePayload,
                                             UserEntity userEntity);

    Page<MessageSummary> findPreviousMessages(UUID organization, UUID messageId, UUID conversationId, UserEntity
            userEntity, Pageable pageable);

    Page<MessageSummary> findNextMessages(UUID organization, UUID messageId, UUID conversationId, UserEntity
            userEntity, Pageable pageable);

    void readMessage(UUID organization, UUID messageId, UUID conversationId, UserEntity
            userEntity);
}
