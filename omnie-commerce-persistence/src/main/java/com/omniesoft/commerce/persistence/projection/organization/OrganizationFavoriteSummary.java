package com.omniesoft.commerce.persistence.projection.organization;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
public interface OrganizationFavoriteSummary {
    @Value("#{target.getId().getOrganization()}")
    OrganizationCardSummary getOrganization();


}
