/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.owner.service.organization.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.AuthorityEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRolePermissionEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.entity.enums.AuthorityName;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.account.OwnerRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
@Service
@AllArgsConstructor
public class OwnerAccessControlServiceImpl implements OwnerAccessControlService {

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final OwnerRepository ownerRepository;

    @Override
    public void hasPermissionByOrganization(UserEntity user, UUID organizationId, AdminPermission requiredPermission) {
        OrganizationEntity organization = organizationRepository.findOne(organizationId);
        if (organization == null) {
            throw new UsefulException("Organization with id: " + organizationId + " does not exist", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }

        checkPermission(user, organization, requiredPermission);
    }

    @Override
    public void hasPermissionByRoleId(UserEntity user, UUID roleId, AdminPermission requiredPermission) {
        OrganizationEntity organization = organizationRepository.findByRoleId(roleId);

        checkPermission(user, organization, requiredPermission);
    }

    @Override
    public void hasPermissionByDiscountId(UserEntity user, UUID discountId, AdminPermission requiredPermission) {
        OrganizationEntity organization = organizationRepository.findByDiscountId(discountId);

        checkPermission(user, organization, requiredPermission);
    }

    @Override
    public void hasPermissionByServiceId(UserEntity user, UUID serviceId, AdminPermission requiredPermission) {
        OrganizationEntity organization = organizationRepository.findByServiceId(serviceId);

        checkPermission(user, organization, requiredPermission);


    }

    private void checkPermission(UserEntity user,
                                 OrganizationEntity organization,
                                 AdminPermission requiredPermission) {
        List<AuthorityEntity> authorities = user.getAuthorities();

        if (checkSupperPermissions(authorities)) {
            // TODO: add extended behavoir with verification supper-user permissions
            return;
        }

        // if user is owner  permit all operations
        if (organization.getOwner().getUser().equals(user)) {
            return;
        }

        for (AdminRoleEntity adminRoleEntity : user.getRoles()) {
            if (adminRoleEntity.getOrganization().equals(organization)) {
                if (containsPermission(adminRoleEntity, requiredPermission)) {
                    return;
                }
            }
        }
        throw new UsefulException("Required Permission: " + requiredPermission + " for: " + user.getLogin(), OwnerModuleErrorCodes
                .PERMISSION_DENIED);
    }

    private boolean checkSupperPermissions(List<AuthorityEntity> authorities) {
        return authorities.stream().anyMatch(a -> a.getName().equals(AuthorityName.ROLE_SUPPORT_ADMIN));
    }

    private boolean containsPermission(AdminRoleEntity adminRole, AdminPermission requiredPermission) {
        for (AdminRolePermissionEntity permission : adminRole.getPermissions()) {
            if (permission.getPermission().equals(requiredPermission)) {
                return true;
            }
        }
        return false;
    }
}
