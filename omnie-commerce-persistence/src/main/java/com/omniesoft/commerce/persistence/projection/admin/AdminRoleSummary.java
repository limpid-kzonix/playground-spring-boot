package com.omniesoft.commerce.persistence.projection.admin;

import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationCardSummary;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.10.17
 */
public interface AdminRoleSummary {
    UUID getId();

    String getName();

    OrganizationCardSummary getOrganization();

    List<AdminRolePermissionSummary> getPermissions();

    List<AccountProfileDataSummary> getAdmins();
}


