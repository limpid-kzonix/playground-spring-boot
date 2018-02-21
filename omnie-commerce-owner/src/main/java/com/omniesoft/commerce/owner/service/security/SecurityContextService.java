package com.omniesoft.commerce.owner.service.security;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

public interface SecurityContextService {
    UserEntity getUser();

    boolean isAuthorized();
}
