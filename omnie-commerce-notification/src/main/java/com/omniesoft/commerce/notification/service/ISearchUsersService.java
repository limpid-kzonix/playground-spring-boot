package com.omniesoft.commerce.notification.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.util.Set;
import java.util.UUID;

public interface ISearchUsersService {
    String getOwner(UUID organizationId);

    Set<UserEntity> getAdmins(UUID organizationId);

    Set<UserEntity> getAdminsAndOwner(UUID organizationReceiver);

    UserEntity getUser(String login);
}
