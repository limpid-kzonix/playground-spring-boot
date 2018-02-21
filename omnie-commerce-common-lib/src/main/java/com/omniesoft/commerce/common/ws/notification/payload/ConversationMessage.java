package com.omniesoft.commerce.common.ws.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConversationMessage extends NotificationMessage {

    private Conversation conversation;

    private String userName;

    public ConversationMessage(Conversation conversation) {
        this.conversation = conversation;
    }


    @Override
    public String getStompUserName() {

        return getUserName();
    }

    @Override
    public UUID getOrganizationId() {

        return getConversation().getOrganization().getId();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Message {
    private UUID id;
    private User user;
    private String text;
    private Boolean readOrgStatus;
    private Boolean readUserStatus;
    private LocalDateTime createTime;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Conversation {
    private UUID id;
    private Organization organization;

    private User user;
    private Message message;
}

