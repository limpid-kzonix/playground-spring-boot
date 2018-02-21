package com.omniesoft.commerce.persistence.projection.organization;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 25.10.17
 */
public interface DiscountSummary {
    UUID getId();

    String getName();

    Double getPercent();
}
