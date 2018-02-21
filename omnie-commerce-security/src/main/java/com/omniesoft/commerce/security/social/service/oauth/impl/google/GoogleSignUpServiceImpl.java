package com.omniesoft.commerce.security.social.service.oauth.impl.google;

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
@Service("googleSingUpService")
public class GoogleSignUpServiceImpl implements OAuthSignUpService {


    private final OAuthApiExplorer oAuthApiExplorer;

    private final SignUpAdapter signUpAdapter;

    public GoogleSignUpServiceImpl(@Qualifier("googleApiExplorer") OAuthApiExplorer oAuthApiExplorer,
                                   SignUpAdapter signUpAdapter) {

        this.oAuthApiExplorer = oAuthApiExplorer;
        this.signUpAdapter = signUpAdapter;
    }

    @Override
    @Transactional
    public UserEntity signUp(String oAuthToken, OAuthClient client) {

        SocialProfile socialProfile = oAuthApiExplorer.fetchUserProfile(oAuthToken);
        return signUpAdapter.singUp(client, socialProfile);

    }


}
