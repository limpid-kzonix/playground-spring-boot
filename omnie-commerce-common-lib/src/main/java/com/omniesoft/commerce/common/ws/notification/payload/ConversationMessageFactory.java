package com.omniesoft.commerce.common.ws.notification.payload;

import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.entity.conversation.MessageEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConversationMessageFactory {

    private static Conversation create(MessageEntity messageEntity) {

        ConversationEntity conversationEntity = messageEntity.getConversation();

        Conversation conversation = new Conversation();
        conversation.setId(conversationEntity.getId());
        conversation.setOrganization(EnhancedMessageFactory.extract(conversationEntity.getOrganization()));
        conversation.setMessage(extract(messageEntity));

        return conversation;
    }

    private static Message extract(MessageEntity messageEntity) {

        Message message = new Message();

        message.setId(messageEntity.getId());
        message.setText(messageEntity.getMessage());
        message.setUser(EnhancedMessageFactory.extract(messageEntity.getSender()));
        message.setCreateTime(messageEntity.getCreateTime());
        message.setReadOrgStatus(messageEntity.getReadOrgStatus());
        message.setReadUserStatus(messageEntity.getReadUserStatus());

        return message;
    }

    private static Conversation create(ConversationEntity conversationEntity) {

        Conversation conversation = new Conversation();
        conversation.setId(conversationEntity.getId());
        conversation.setOrganization(EnhancedMessageFactory.extract(conversationEntity.getOrganization()));
        conversation.setUser(EnhancedMessageFactory.extract(conversationEntity.getUser()));
        log.info(conversation.toString());
        return conversation;
    }

    public static ConversationMessage compact(String recipientUserName, ConversationEntity conversationEntity) {

        Conversation conversation = create(conversationEntity);
        return new ConversationMessage(conversation, recipientUserName);
    }

    public static ConversationMessage compact(ConversationEntity conversationEntity) {

        Conversation conversation = create(conversationEntity);
        return new ConversationMessage(conversation);
    }

    public static ConversationMessage compact(MessageEntity messageEntity) {
        Conversation conversation = create(messageEntity);
        return new ConversationMessage(conversation);

    }
}
