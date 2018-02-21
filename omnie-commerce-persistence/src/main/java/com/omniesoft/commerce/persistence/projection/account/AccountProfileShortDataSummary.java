package com.omniesoft.commerce.persistence.projection.account;

import java.util.UUID;

public interface AccountProfileShortDataSummary {

    UUID getId();

    String getLogin();

    String getEmail();

    AccountProfileSummary getProfile();

}
