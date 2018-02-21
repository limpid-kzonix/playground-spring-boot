package com.omniesoft.commerce.persistence.repository.conversation;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;

import java.util.UUID;

public interface MessageRepositoryCustom {

    void readMessages(UUID conversation, UserEntity userEntity);

    void readMessages(UUID conversation, OrganizationEntity organizationEntity, UserEntity userEntity);
}
