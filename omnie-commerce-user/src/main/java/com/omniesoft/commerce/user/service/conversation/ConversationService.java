package com.omniesoft.commerce.user.service.conversation;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface ConversationService {

    Page<ConversationSummary> findUsersConversations(String search, UserEntity userEntity, Pageable pageable);

    UUID createConversation(UUID org, UserEntity userEntity);

    MessageSummary fetchLastMessageInConversation(UUID conversationId, UserEntity userEntity);


}
