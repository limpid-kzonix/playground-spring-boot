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
import com.omniesoft.commerce.owner.converter.PersonnelManagementConverter;
import com.omniesoft.commerce.owner.service.organization.PersonnelManagementService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRolePermissionEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.projection.admin.AdminRoleSummary;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.admin.AdminRolePermissionRepository;
import com.omniesoft.commerce.persistence.repository.admin.AdminRoleRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
@Service
@AllArgsConstructor
public class PersonnelManagementServiceImpl implements PersonnelManagementService {
    private final Logger log = LoggerFactory.getLogger(PersonnelManagementServiceImpl.class);

    private final AdminRolePermissionRepository adminRolePermissionRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final PersonnelManagementConverter converter;

    @Override
    public UUID saveRole(UUID organizationId, String roleName, UserEntity user) {

        OrganizationEntity organization = organizationRepository.findOne(organizationId);

        AdminRoleEntity adminRoleEntity = new AdminRoleEntity(roleName, organization);

        adminRoleRepository.save(adminRoleEntity);
        return adminRoleEntity.getId();
    }

    @Override
    public List<AdminRoleSummary> getRoles(UUID organizationId) {
        List<AdminRoleSummary> allByOrganizationId = adminRoleRepository.findAllByOrganizationId(organizationId);
        if (allByOrganizationId != null && !allByOrganizationId.isEmpty())
            return allByOrganizationId;
        else
            throw new UsefulException("Organization with id [ " + organizationId + " ] has not created roles", InternalErrorCodes.RESOURCE_NOT_FOUND);
    }

    @Override
    public void deleteRole(UUID organizationId, UUID roleId) {
        adminRoleRepository.delete(organizationId, roleId);
    }

    @Override
    public void updateRoleName(UUID organizationId, UUID roleId, String name) {
        adminRoleRepository.updateRoleName(organizationId, roleId, name);
    }

    @Override
    public UUID addPermissionToRole(UUID organizationId, UUID roleId, AdminPermission permission) {
        AdminRoleEntity role = adminRoleRepository.findAllByIdAndOrganizationId(roleId, organizationId);
        //FIXME motokyi at 17.10.17: check nn
        AdminRolePermissionEntity permissionEntity = new AdminRolePermissionEntity(role, permission);
        try {
            adminRolePermissionRepository.save(permissionEntity);
            return permissionEntity.getId();
        } catch (DataIntegrityViolationException e) {
            log.warn("Already exist", e);
            if (role.getPermissions() != null) {
                for (AdminRolePermissionEntity adminPermission : role.getPermissions()) {
                    if (permission.equals(adminPermission.getPermission())) {
                        return adminPermission.getId();
                    }
                }

            }
        }
        return null;
    }


    @Override
    public void deleteRolePermission(UUID organizationId, UUID roleId, UUID permissionId) {
        AdminRoleEntity role = adminRoleRepository.findAllByIdAndOrganizationId(roleId, organizationId);
        if (role != null) {
            try {
                adminRolePermissionRepository.delete(permissionId);
            } catch (EmptyResultDataAccessException e) {
                log.warn("Delete not exist permission", e);
            }
        }
    }

    @Override
    public void addUserToRole(UUID organizationId, UUID roleId, UUID userId) {
        AdminRoleEntity role = adminRoleRepository.findAllByIdAndOrganizationId(roleId, organizationId);
        UserEntity user = userRepository.findOne(userId);

        if (role != null && user != null) {
            role.getAdmins().add(user);
            adminRoleRepository.save(role);
        }
    }

    @Override
    public void deleteUserFromRole(UUID organizationId, UUID roleId, UUID userId) {
        UserEntity user = userRepository.findOne(userId);
        AdminRoleEntity role = adminRoleRepository.findAllByIdAndOrganizationId(roleId, organizationId);
        if (role != null && user != null) {
            user.getRoles().remove(role);

            if (!hasAnyRolesInThisOrganization(user, role.getOrganization().getId())) {
                user.getAdminAtServices().removeIf(serviceEntity ->
                        serviceEntity.getOrganization().getId().equals(organizationId));

            }
            userRepository.save(user);
        }
    }

    private boolean hasAnyRolesInThisOrganization(UserEntity user, UUID organizationId) {
        for (AdminRoleEntity role : user.getRoles()) {
            if (role.getOrganization().getId().equals(organizationId))
                return true;
        }
        return false;
    }

    @Override
    public void addAdminToService(UUID organizationId, UUID userId, UUID serviceId) {
        try {
            ServiceEntity service = serviceRepository.findByIdAndOrganizationId(serviceId,
                    organizationId);
            UserEntity user = userRepository.findOne(userId);
            //don`t add to service if user haven`t any role
            if (hasAnyRolesInThisOrganization(user, organizationId)) {
                Set<ServiceEntity> adminAtServices = user.getAdminAtServices();
                adminAtServices.add(service);
                userRepository.save(user);
            }
        } catch (Exception e) {
            log.warn("Error during add admin to service", e);
        }
    }

    @Override
    public void deleteAdminFromService(UUID organizationId, UUID userId, UUID serviceId) {
        try {
            UserEntity user = userRepository.findOne(userId);
            Set<ServiceEntity> adminAtServices = user.getAdminAtServices();
            adminAtServices.removeIf(service -> service.getId().equals(serviceId));
            userRepository.save(user);
        } catch (Exception e) {
            log.warn("Error during delete admin for service", e);
        }
    }


}
