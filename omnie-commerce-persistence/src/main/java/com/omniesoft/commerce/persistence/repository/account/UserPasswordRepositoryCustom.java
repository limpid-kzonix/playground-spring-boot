package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserPasswordResetEntity;


public interface UserPasswordRepositoryCustom {

    UserPasswordResetEntity findWithUser(String userEmail, Integer code);

    UserPasswordResetEntity findWithUser(String userEmail, String resetToken);
}
