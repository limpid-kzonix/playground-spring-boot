package com.omniesoft.commerce.owner.service.permissions.impl;

import com.omniesoft.commerce.owner.controller.management.payload.AdminRolePermissionDto;
import com.omniesoft.commerce.owner.controller.management.payload.PermissionByOrganizationPayload;
import com.omniesoft.commerce.owner.converter.PersonnelManagementConverter;
import com.omniesoft.commerce.owner.service.permissions.PermissionsService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRolePermissionEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.admin.AdminRolePermissionRepository;
import com.omniesoft.commerce.persistence.repository.admin.AdminRoleRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
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
    private final Logger log = LoggerFactory.getLogger(PermissionsServiceImpl.class);

    private final AdminRolePermissionRepository adminRolePermissionRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final PersonnelManagementConverter converter;


    @Override
    public List<PermissionByOrganizationPayload> getAllPermissionsByUser(UserEntity user) {
        List<OrganizationEntity> organizations = organizationRepository.findByOwnerOrAdmin(user);
        List<PermissionByOrganizationPayload> result = new ArrayList<>();
        Set<AdminRolePermissionDto> allPermissions = getAllPermissions();
        for (OrganizationEntity organization : organizations) {
            if (organization.getOwner().getUser().equals(user)) {
                result.add(new PermissionByOrganizationPayload()
                        .setOrganizationId(organization.getId())
                        .setOrganizationName(organization.getName())
                        .setPermissions(allPermissions)
                );

            }

        }
        return result;
    }

    private Set<AdminRolePermissionDto> getAllPermissions() {
        Set<AdminRolePermissionDto> result = new HashSet<>();
        for (AdminRolePermissionEntity permissionEntity : adminRolePermissionRepository.findAll()) {
            result.add(new AdminRolePermissionDto()
                    .setId(permissionEntity.getId())
                    .setPermission(permissionEntity.getPermission()));
        }

        return result;
    }
}
