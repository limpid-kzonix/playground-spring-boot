package com.omniesoft.commerce.user.service.account;

import com.omniesoft.commerce.common.payload.account.AccountDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.account.payload.UserRegisterPayload;

public interface AccountService {
    AccountDto register(UserRegisterPayload user);

    UserEntity getUserInfo(String username);

}
