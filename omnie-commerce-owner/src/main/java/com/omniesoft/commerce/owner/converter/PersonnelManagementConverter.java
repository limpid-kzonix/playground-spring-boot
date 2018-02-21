package com.omniesoft.commerce.owner.converter;

import com.omniesoft.commerce.owner.controller.management.payload.AdminRoleDto;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;

import java.util.Set;

/**
 * @author Vitalii Martynovskyi
 * @since 17.10.17
 */
public interface PersonnelManagementConverter {
    Set<AdminRoleDto> roles(Set<AdminRoleEntity> roles);

}
