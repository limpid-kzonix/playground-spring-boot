package com.omniesoft.commerce.persistence.repository.admin;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;

import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.10.17
 */
public interface AdminRoleRepositoryCustom {
    void delete(UUID organizationId, UUID roleId);

    Set<AdminRoleEntity> findByRolesOrganizationId(UUID organizationId);

    Set<UserEntity> findAdminsByOrganizationIdFetchOauth(UUID organizationId);

    AdminRoleEntity findByOrganizationAndUser(OrganizationEntity org, UserEntity admin);

    Set<AdminRoleEntity> findByOrganizationIdWithPermissionsAndAdmins(UUID organizationId);

    void updateRoleName(UUID organizationId, UUID roleId, String name);
}
