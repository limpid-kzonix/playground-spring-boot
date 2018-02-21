package com.omniesoft.commerce.persistence.projection.organization;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
public interface OrganizationCardSummary {
    UUID getId();

    String getName();

    String getLogoId();

    String getPlaceId();

    Double getLongitude();

    Double getLatitude();

    Boolean getFreezeStatus();

    String getReason();
}
