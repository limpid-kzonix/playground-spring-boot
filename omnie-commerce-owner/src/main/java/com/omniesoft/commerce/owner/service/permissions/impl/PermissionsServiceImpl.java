package com.omniesoft.commerce.owner.service.permissions.impl;

import com.google.common.collect.Sets;
import com.omniesoft.commerce.common.Constants;
import com.omniesoft.commerce.owner.controller.management.payload.PermissionByOrganizationPayload;
import com.omniesoft.commerce.owner.service.permissions.PermissionsService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRolePermissionEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.admin.AdminRolePermissionRepository;
import com.omniesoft.commerce.persistence.repository.admin.AdminRoleRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionsServiceImpl implements PermissionsService {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionsServiceImpl.class);


    private final AdminRolePermissionRepository adminRolePermissionRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final OrganizationRepository organizationRepository;


    @Override
    public List<PermissionByOrganizationPayload> getAllPermissionsByUser(UserEntity user) {
        List<OrganizationEntity> organizations = organizationRepository.findByOwnerOrAdmin(user);
        List<PermissionByOrganizationPayload> result = new ArrayList<>();

        for (OrganizationEntity organization : organizations) {

            PermissionByOrganizationPayload e = new PermissionByOrganizationPayload();

            e.setOrganizationId(organization.getId())
                    .setOrganizationName(organization.getName())
                    .setOrganizationLogoId(organization.getLogoId());

            if (organization.getOwner().getUser().equals(user)) {

                e.setRoleType(Constants.Role.TYPE_OWNER)
                        .setPermissions(getAllPermissions());

            } else {

                AdminRoleEntity role = adminRoleRepository.findByOrganizationAndUser(organization, user);
                e.setRoleType(Constants.Role.TYPE_ADMIN)
                        .setRoleName(role.getName())
                        .setPermissions(convertToAdminRolePermissionDtos(role.getPermissions()));
            }
            result.add(e);
        }
        return result;
    }

    private Set<AdminPermission> getAllPermissions() {
        return Sets.newHashSet(AdminPermission.values());
    }

    private Set<AdminPermission> convertToAdminRolePermissionDtos(Iterable<AdminRolePermissionEntity> all) {
        Set<AdminPermission> result = new HashSet<>();
        for (AdminRolePermissionEntity permissionEntity : all) {
            result.add(permissionEntity.getPermission());
        }

        return result;
    }
}
