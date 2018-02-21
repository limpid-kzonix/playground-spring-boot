package com.omniesoft.commerce.persistence.projection.account;

import java.util.UUID;

public interface AccountProfileShortSummary {

    UUID getId();

    String getFirstName();

    String getLastName();

    String getOmnieCard();
}
