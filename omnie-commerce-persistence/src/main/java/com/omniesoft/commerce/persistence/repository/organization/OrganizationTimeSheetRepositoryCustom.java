package com.omniesoft.commerce.persistence.repository.organization;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 10.10.17
 */
public interface OrganizationTimeSheetRepositoryCustom {
    void delete(UUID organizationId, UUID timesheetId);
}
