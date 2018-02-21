package com.omniesoft.commerce.persistence.repository.admin;

import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;

import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.10.17
 */
public interface AdminRoleRepositoryCustom {
    void delete(UUID organizationId, UUID roleId);

    Set<AdminRoleEntity> findByRolesOrganizationId(UUID organizationId);

    Set<AdminRoleEntity> findByOrganizationIdWithPermissionsAndAdmins(UUID organizationId);

    void updateRoleName(UUID organizationId, UUID roleId, String name);
}
