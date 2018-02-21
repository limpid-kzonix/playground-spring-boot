package com.omniesoft.commerce.security.social.service.oauth.impl.facebook;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.OAuthApiExplorer;
import com.omniesoft.commerce.security.social.service.oauth.OAuthSignUpService;
import com.omniesoft.commerce.security.social.service.oauth.SignUpAdapter;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("facebookSingUpService")
public class FacebookSignUpServiceImpl implements OAuthSignUpService {


    private final OAuthApiExplorer oAuthApiExplorer;

    private final SignUpAdapter signUpAdapter;


    public FacebookSignUpServiceImpl(@Qualifier("facebookApiExplorer") OAuthApiExplorer oAuthApiExplorer,
                                     SignUpAdapter signUpAdapter) {

        this.oAuthApiExplorer = oAuthApiExplorer;
        this.signUpAdapter = signUpAdapter;
    }

    @Override
    @Transactional
    public UserEntity signUp(String oAuthToken, OAuthClient client) {

        SocialProfile socialProfile = oAuthApiExplorer.fetchUserProfile(oAuthToken);
        UserEntity savedUser = signUpAdapter.singUp(client, socialProfile);
        return savedUser;


    }


}
