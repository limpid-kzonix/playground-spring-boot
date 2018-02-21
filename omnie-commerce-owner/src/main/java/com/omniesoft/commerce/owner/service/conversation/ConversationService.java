package com.omniesoft.commerce.owner.service.conversation;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConversationService {


    Page<ConversationSummary> findOrganizationConversations(
            UUID organization,
            String searchPattern,
            UserEntity userEntity,
            Pageable pageable);

    UUID createConversation(UUID org, UUID conversationUserId, UserEntity userEntity);


    MessageSummary fetchLastMessageInConversation(UUID org, UUID conversationId, UserEntity userEntity);


}
