package com.omniesoft.commerce.security.social.service.oauth;

import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;

public interface OAuthProfileValidator {
    void validate(SocialProfile socialProfile);
}
