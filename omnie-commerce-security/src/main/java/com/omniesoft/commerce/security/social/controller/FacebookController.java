package com.omniesoft.commerce.security.social.controller;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.OAuthHandlerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/social/facebook", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacebookController {


    private OAuthHandlerAdapter oAuthHandlerAdapter;

    public FacebookController(@Qualifier("facebookOAuthHandlerService") OAuthHandlerAdapter oAuthHandlerAdapter) {

        this.oAuthHandlerAdapter = oAuthHandlerAdapter;
    }

    @PutMapping(path = "/link")
    public void linkFacebookSocialProfileToUser(
            @RequestParam("social_token") String facebookToken,
            @ApiIgnore UserEntity userEntity
    ) {

        oAuthHandlerAdapter.link(facebookToken, userEntity, OAuthClient.FACEBOOK);
    }

    @PostMapping()
    public OAuth2AccessToken signInProfileToUser(
            @RequestParam("social_token") String facebookToken) {

        return oAuthHandlerAdapter.handle(facebookToken, OAuthClient.FACEBOOK);
    }


}
