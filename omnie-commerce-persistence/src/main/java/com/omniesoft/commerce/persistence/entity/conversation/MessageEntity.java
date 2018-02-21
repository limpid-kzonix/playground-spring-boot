package com.omniesoft.commerce.persistence.entity.conversation;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.MessageSender;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.09.17
 */
@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @Column(name = "read_user_status")
    private Boolean readUserStatus;

    @Column(name = "read_org_status")
    private Boolean readOrgStatus;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "sent_by", nullable = false, insertable = true, updatable = false)
    @Enumerated(EnumType.STRING)
    private MessageSender sentBy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public MessageSender getSentBy() {

        return sentBy;
    }

    public void setSentBy(MessageSender sentBy) {

        this.sentBy = sentBy;
    }

    public Boolean getReadOrgStatus() {

        return readOrgStatus;
    }

    public void setReadOrgStatus(Boolean readOrgStatus) {

        this.readOrgStatus = readOrgStatus;
    }

    public Boolean getReadUserStatus() {

        return readUserStatus;
    }

    public void setReadUserStatus(Boolean readUserStatus) {

        this.readUserStatus = readUserStatus;
    }
}
