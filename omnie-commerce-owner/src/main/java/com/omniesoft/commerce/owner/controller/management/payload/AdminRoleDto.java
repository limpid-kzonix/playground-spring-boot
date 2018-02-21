package com.omniesoft.commerce.owner.controller.management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleDto {
    private UUID id;
    private String name;
    private UUID organizationId;
    private Set<AdminRolePermissionDto> permissions;
    private Set<UserDto> admins;
}
