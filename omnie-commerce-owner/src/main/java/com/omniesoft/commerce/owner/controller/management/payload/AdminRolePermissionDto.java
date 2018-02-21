package com.omniesoft.commerce.owner.controller.management.payload;

import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRolePermissionDto {
    private UUID id;
    private AdminPermission permission;
}
