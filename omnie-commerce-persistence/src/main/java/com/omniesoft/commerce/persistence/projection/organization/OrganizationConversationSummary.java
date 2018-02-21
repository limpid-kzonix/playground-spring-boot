package com.omniesoft.commerce.persistence.projection.organization;

import java.util.UUID;

public interface OrganizationConversationSummary {
    UUID getId();

    String getName();

    String getEmail();

    String getTitle();

    String getDescription();

    String getLogoId();
}
