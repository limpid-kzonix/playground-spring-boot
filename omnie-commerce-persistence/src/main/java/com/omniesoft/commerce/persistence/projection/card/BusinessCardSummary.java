package com.omniesoft.commerce.persistence.projection.card;

import java.util.UUID;

public interface BusinessCardSummary {

    UUID getId();

    String getImage();

    String getName();

    String getEmail();

    String getPhone();

    String getComment();
}
