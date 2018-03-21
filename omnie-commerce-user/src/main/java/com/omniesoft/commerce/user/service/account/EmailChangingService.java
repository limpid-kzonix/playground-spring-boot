package com.omniesoft.commerce.user.service.account;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

public interface EmailChangingService {
    void changeEmail(UserEntity userEntity, String newEmail);

    void changeEmail(String token);
}
