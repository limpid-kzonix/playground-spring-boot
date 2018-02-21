package com.omniesoft.commerce.security.social.controller;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.OAuthSignInService;
import com.omniesoft.commerce.security.social.service.oauth.OAuthSignUpService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/social/facebook", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacebookController {


    private OAuthSignUpService signUpService;


    private OAuthSignInService signInService;

    public FacebookController(@Qualifier("facebookSingUpService") OAuthSignUpService signUpService,
                              @Qualifier("facebookSingInService") OAuthSignInService signInService) {

        this.signUpService = signUpService;
        this.signInService = signInService;
    }

    @PutMapping(path = "/link")
    public void linkFacebookSocialProfileToUser(
            @RequestParam("social_token") String facebookToken,
            @ApiIgnore UserEntity userEntity
    ) {

        signInService.link(facebookToken, userEntity, OAuthClient.FACEBOOK);
    }

    @PostMapping(path = "/signup")
    public void signUpProfileToUser(
            @RequestParam("social_token") String facebookToken
    ) {

        signUpService.signUp(facebookToken, OAuthClient.FACEBOOK);
    }

    @PostMapping(path = "/signin")
    public OAuth2AccessToken signInProfileToUser(
            @RequestParam("social_token") String facebookToken
    ) {

        return signInService.signIn(facebookToken, OAuthClient.FACEBOOK);
    }


}
