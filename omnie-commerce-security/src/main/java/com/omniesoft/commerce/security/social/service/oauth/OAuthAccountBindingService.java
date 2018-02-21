package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserOAuthEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;

public interface OAuthAccountBindingService {

    UserEntity fetchUserLinkedOAuthData(SocialProfile facebookUserProfile, OAuthClient client);

    boolean isLinked(SocialProfile facebookUserProfile, OAuthClient client);

    UserOAuthEntity link(UserEntity userEntity, SocialProfile facebookUserProfile, OAuthClient client);

}
