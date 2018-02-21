package com.omniesoft.commerce.user.service.security;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

public interface SecurityContextService {
    UserEntity getUser();

    boolean isAuthorized();

    String getUserId();
}
