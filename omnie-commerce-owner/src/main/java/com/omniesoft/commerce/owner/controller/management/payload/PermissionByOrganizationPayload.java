package com.omniesoft.commerce.owner.controller.management.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
//@Accessors(chain = true)
@NoArgsConstructor
public class PermissionByOrganizationPayload {
    private UUID organizationId;
    private String organizationName;
    private Set<AdminRolePermissionDto> permissions;
}
