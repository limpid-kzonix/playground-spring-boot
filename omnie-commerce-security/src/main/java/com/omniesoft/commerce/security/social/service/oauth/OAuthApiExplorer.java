package com.omniesoft.commerce.security.social.service.oauth;


import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;

public interface OAuthApiExplorer {

    SocialProfile fetchUserProfile(String facebookToken);

}
