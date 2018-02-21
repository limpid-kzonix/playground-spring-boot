package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;

public interface OAuthSignUpService {

    UserEntity signUp(String facebookToken, OAuthClient client);

}
