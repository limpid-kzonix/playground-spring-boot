package com.omniesoft.commerce.user.service.profile;

import com.omniesoft.commerce.common.payload.account.AccountDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.profile.payload.UserProfilePayload;

public interface UserProfileService {

    AccountDto save(UserProfilePayload userProfile, UserEntity userEntity);

    AccountDto find(UserEntity userEntity);
}

