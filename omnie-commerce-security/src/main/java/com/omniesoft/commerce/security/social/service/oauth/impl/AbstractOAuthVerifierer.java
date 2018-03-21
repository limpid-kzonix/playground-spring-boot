package com.omniesoft.commerce.security.social.service.oauth.impl;

import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.persistence.repository.account.UserOAuthRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;

public abstract class AbstractOAuthVerifierer {

    private final UserRepository userRepository;
    private final UserOAuthRepository userOAuthRepository;

    protected AbstractOAuthVerifierer(UserRepository userRepository, UserOAuthRepository userOAuthRepository) {
        this.userRepository = userRepository;
        this.userOAuthRepository = userOAuthRepository;
    }

    protected boolean isLinked(SocialProfile socialProfile, OAuthClient oAuthClient) {
        return userOAuthRepository.findByProfileIdAndOauthClient(socialProfile.getId(), oAuthClient) != null;
    }

    protected boolean isRegistered(SocialProfile socialProfile) {
        return userRepository.findByEmail(socialProfile.getEmail()) != null;
    }
}
