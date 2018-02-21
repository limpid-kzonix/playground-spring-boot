package com.omniesoft.commerce.persistence.projection.conversation;

import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationConversationSummary;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ConversationSummary {
    UUID getId();

    OrganizationConversationSummary getOrganization();

    AccountProfileDataSummary getUser();

    LocalDateTime getCreateTime();
}
