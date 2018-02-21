package com.omniesoft.commerce.persistence.projection.admin;

import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.10.17
 */
public interface AdminRolePermissionSummary {
    UUID getId();

    AdminPermission getPermission();
}
