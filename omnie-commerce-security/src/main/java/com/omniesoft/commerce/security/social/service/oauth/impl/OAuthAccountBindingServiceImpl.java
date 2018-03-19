package com.omniesoft.commerce.security.social.service.oauth.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserOAuthEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.persistence.repository.account.UserOAuthRepository;
import com.omniesoft.commerce.security.social.service.oauth.OAuthAccountBindingService;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuthAccountBindingServiceImpl implements OAuthAccountBindingService {

    private final UserOAuthRepository userOAuthRepository;


    @Override
    public UserEntity fetchUserLinkedOAuthData(SocialProfile socialUserProfile, OAuthClient client) {

        UserOAuthEntity byProfileIdAndOauthClient = userOAuthRepository
                .findByProfileIdAndOauthClient(socialUserProfile.getId(), client);
        if (null == byProfileIdAndOauthClient) {
            return null;
        }
        return byProfileIdAndOauthClient.getUser();
    }

    @Override
    public boolean isLinked(SocialProfile socialUserProfile, OAuthClient client) {

        return null != fetchUserLinkedOAuthData(socialUserProfile, client);
    }

    @Override
    public UserOAuthEntity link(UserEntity userEntity, SocialProfile facebookUserProfile, OAuthClient client) {

        if (isLinked(facebookUserProfile, client)) {
            throw new UsefulException("Social account already linked to account", SecurityModuleErrorCodes.SOCIAL_CONNECT_ERROR);
        }
        UserOAuthEntity userOAuthEntity = new UserOAuthEntity();
        userOAuthEntity.setOauthClient(client);
        userOAuthEntity.setUser(userEntity);
        userOAuthEntity.setProfileId(facebookUserProfile.getId());
        return userOAuthRepository.save(userOAuthEntity);
    }
}
