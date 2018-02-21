package com.omniesoft.commerce.security.social.service.oauth.impl.google;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.OAuthApiExplorer;
import com.omniesoft.commerce.security.social.service.oauth.OAuthSignInService;
import com.omniesoft.commerce.security.social.service.oauth.SignInAdapter;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service("googleSingInService")
public class GoogleSingInServiceImpl implements OAuthSignInService {

    private final OAuthApiExplorer oAuthApiExplorer;

    private final SignInAdapter signInAdapter;

    public GoogleSingInServiceImpl(@Qualifier("googleApiExplorer") OAuthApiExplorer oAuthApiExplorer,
                                   SignInAdapter signInAdapter) {

        this.oAuthApiExplorer = oAuthApiExplorer;
        this.signInAdapter = signInAdapter;
    }

    @Override
    public OAuth2AccessToken signIn(String oAuthToken, OAuthClient client) {

        SocialProfile facebookProfile = oAuthApiExplorer
                .fetchUserProfile(oAuthToken);
        return signInAdapter.singIn(client, facebookProfile);
    }


    @Override
    public void link(String oAuthToken, UserEntity userEntity, OAuthClient client) {

        SocialProfile userProfile = oAuthApiExplorer
                .fetchUserProfile(oAuthToken);
        signInAdapter.link(userEntity, client, userProfile);
    }


}
