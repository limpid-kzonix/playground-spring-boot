package com.omniesoft.commerce.persistence.projection.card;

import java.util.UUID;

public interface DiscountCardSummary {
    UUID getId();

    String getImage();

    String getName();

    String getCode();

    String getFormat();
}
