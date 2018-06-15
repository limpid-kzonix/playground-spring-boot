package com.omniesoft.commerce.security.social.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

public interface IFCMService {
    void link(UserEntity user, String fcmToken);

}
