package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;

public interface SignUpAdapter {

    UserEntity singUp(OAuthClient client, SocialProfile socialProfile);

}
