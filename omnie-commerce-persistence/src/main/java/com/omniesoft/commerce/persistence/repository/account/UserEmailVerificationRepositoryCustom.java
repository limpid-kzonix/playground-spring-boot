package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserEmailVerificationEntity;

public interface UserEmailVerificationRepositoryCustom {
    UserEmailVerificationEntity findByToken(String token);
}
