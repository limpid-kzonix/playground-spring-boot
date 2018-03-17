package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;

public interface OAuthSignUpService {

    UserEntity signUp(SocialProfile socialProfile, OAuthClient client);

}
