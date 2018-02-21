package com.omniesoft.commerce.persistence.projection.conversation;

import com.omniesoft.commerce.persistence.entity.enums.MessageSender;
import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MessageSummary {

    UUID getId();

    Boolean getReadOrgStatus();

    Boolean getReadUserStatus();

    String getMessage();

    AccountProfileDataSummary getSender();

    MessageSender getSentBy();

    LocalDateTime getCreateTime();


}
