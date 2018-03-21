package com.omniesoft.commerce.security.social.service.oauth.impl.facebook;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.persistence.repository.account.UserOAuthRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.security.social.service.oauth.OAuthApiExplorer;
import com.omniesoft.commerce.security.social.service.oauth.OAuthHandlerAdapter;
import com.omniesoft.commerce.security.social.service.oauth.OAuthSignInService;
import com.omniesoft.commerce.security.social.service.oauth.OAuthSignUpService;
import com.omniesoft.commerce.security.social.service.oauth.SignInAdapter;
import com.omniesoft.commerce.security.social.service.oauth.SignUpAdapter;
import com.omniesoft.commerce.security.social.service.oauth.impl.AbstractOAuthVerifierer;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("facebookOAuthHandlerService")
public class FacebookOAuthHandlerServiceImpl extends AbstractOAuthVerifierer implements OAuthSignUpService, OAuthSignInService, OAuthHandlerAdapter {


  private final OAuthApiExplorer oAuthApiExplorer;
  private final SignInAdapter signInAdapter;
  private final SignUpAdapter signUpAdapter;


  public FacebookOAuthHandlerServiceImpl(@Qualifier("facebookApiExplorer") OAuthApiExplorer oAuthApiExplorer,
      SignInAdapter signInAdapter, SignUpAdapter signUpAdapter, UserRepository userRepository, UserOAuthRepository userOAuthRepository) {
    super(userRepository, userOAuthRepository);
    this.oAuthApiExplorer = oAuthApiExplorer;
    this.signInAdapter = signInAdapter;
    this.signUpAdapter = signUpAdapter;
  }

  @Override
  @Transactional
  public UserEntity signUp(SocialProfile profile, OAuthClient client) {

    UserEntity savedUser = signUpAdapter.singUp(client, profile);
    return savedUser;


  }


  @Override
  public OAuth2AccessToken signIn(SocialProfile profile, OAuthClient client) {
    return signInAdapter.singIn(client, profile);
  }

  @Override
  public OAuth2AccessToken handle(String oAuthToken, OAuthClient client) {
    SocialProfile profile = oAuthApiExplorer
        .fetchUserProfile(oAuthToken);
    if (!isRegistered(profile) && !isLinked(profile, client)) {
      signUp(profile, client);
      return signIn(profile, client);
    } else if (isLinked(profile, client)) {
      return signIn(profile, client);
    } else {
      throw new UsefulException("User have not used social accounts yet. Use 'link' operation").withCode(SecurityModuleErrorCodes.SOCIAL_NOT_CONNECTED);
    }
  }

  @Override
  public void link(String oAuthToken, UserEntity userEntity, OAuthClient client) {

    SocialProfile userProfile = oAuthApiExplorer
        .fetchUserProfile(oAuthToken);
    signInAdapter.link(userEntity, client, userProfile);
  }
}
