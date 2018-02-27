package com.omniesoft.commerce.owner.service.permissions;

import com.omniesoft.commerce.owner.controller.management.payload.PermissionByOrganizationPayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.util.List;

public interface PermissionsService {
    List<PermissionByOrganizationPayload> getAllPermissionsByUser(UserEntity user);

}
