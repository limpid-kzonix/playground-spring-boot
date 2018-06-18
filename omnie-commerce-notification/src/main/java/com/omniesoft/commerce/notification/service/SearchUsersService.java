package com.omniesoft.commerce.notification.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.admin.AdminRoleRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class SearchUsersService implements ISearchUsersService {

    private final OrganizationRepository organizationRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final UserRepository userRepository;

    @Override
    public String getOwner(UUID organizationId) {
        return organizationRepository.findOwnerLogin(organizationId);
    }

    public UserEntity getOwnerAccount(UUID organizationId) {
        return organizationRepository.findOwnerFetchOauth(organizationId);
    }

    @Override
    public Set<UserEntity> getAdmins(UUID organizationId) {
        return adminRoleRepository.findAdminsByOrganizationIdFetchOauth(organizationId);
    }

    @Override
    public Set<UserEntity> getAdminsAndOwner(UUID organizationId) {
        Set<UserEntity> admins = getAdmins(organizationId);
        admins.add(getOwnerAccount(organizationId));
        return admins;
    }

    @Override
    public UserEntity getUser(String login) {
        return userRepository.findByLoginFetchOAuth(login);
    }
}
