package com.omniesoft.commerce.user.service.account;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

public interface EmailConfirmationService {
    void setEmailToken(UserEntity userEntity, String token);

    boolean verifyEmailToken(String token);

    void replyEmailTokenSending(String email);
}
