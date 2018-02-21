package com.omniesoft.commerce.persistence.repository.organization;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 05.10.17
 */
public interface OrganizationGalleryRepositoryCustom {

    void removeImages(UUID imageId, UUID organizationId);

}
