package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface OAuthHandlerAdapter {

    OAuth2AccessToken handle(String facebookToken, OAuthClient client);

    void link(String facebookToken, UserEntity userEntity, OAuthClient client);
}
