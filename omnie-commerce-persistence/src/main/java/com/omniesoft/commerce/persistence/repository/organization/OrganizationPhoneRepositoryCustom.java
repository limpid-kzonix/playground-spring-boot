package com.omniesoft.commerce.persistence.repository.organization;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
public interface OrganizationPhoneRepositoryCustom {
    void delete(UUID organizationId, UUID phoneId);
}
