package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface SignInAdapter {

    void link(UserEntity userEntity, OAuthClient client, SocialProfile userProfile);

    OAuth2AccessToken singIn(OAuthClient client, SocialProfile facebookProfile);

}
