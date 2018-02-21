package com.omniesoft.commerce.security.social.service.oauth.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.security.social.service.oauth.OAuthAccountBindingService;
import com.omniesoft.commerce.security.social.service.oauth.SignInAdapter;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SignInAdapterImpl implements SignInAdapter {

    private final OAuthAccountBindingService oAuthAccountBindingService;

    private final DefaultTokenServices tokenService;

    @Override
    public OAuth2AccessToken singIn(OAuthClient client, SocialProfile facebookProfile) {

        UserEntity userEntity = oAuthAccountBindingService.fetchUserLinkedOAuthData(facebookProfile, client);
        if (userEntity == null) {
            throw new UsefulException("User does not exist with linked social account (facebook: " + facebookProfile
                    .getName() + ")", SecurityModuleErrorCodes.SOCIAL_NOT_CONNECTED);
        }

        HashMap<String, String> authorizationParameters = authorizationParameters(userEntity);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("SOCIAL_ACCOUNT"));

        Set<String> responseType = getResponseType("password");

        Set<String> scopes = getScopes();

        OAuth2Request authorizationRequest = getOAuth2Request(authorizationParameters, authorities, responseType,
                scopes);

        User user = preparePrincipal(userEntity, authorities);

        OAuth2Authentication authenticationRequest = getOAuth2Authentication(authorities, authorizationRequest, user);

        return tokenService
                .createAccessToken(authenticationRequest);
    }

    private Set<String> getResponseType(String password) {

        Set<String> responseType = new HashSet<String>();
        responseType.add(password);
        return responseType;
    }

    private OAuth2Authentication getOAuth2Authentication(Set<GrantedAuthority> authorities,
                                                         OAuth2Request authorizationRequest, User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, authorities);

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(
                authorizationRequest, authenticationToken);
        authenticationRequest.setAuthenticated(true);
        return authenticationRequest;
    }

    private OAuth2Request getOAuth2Request(HashMap<String, String> authorizationParameters,
                                           Set<GrantedAuthority> authorities, Set<String> responseType,
                                           Set<String> scopes) {

        return new OAuth2Request(
                authorizationParameters, "web-client",
                authorities, true, scopes, null, "",
                responseType, null);
    }

    private Set<String> getScopes() {

        Set<String> scopes = new HashSet<String>();
        scopes.add("read");
        scopes.add("write");
        return scopes;
    }

    private HashMap<String, String> authorizationParameters(UserEntity userEntity) {

        HashMap<String, String> authorizationParameters = new HashMap<String, String>();
        authorizationParameters.put("scope", "read");
        authorizationParameters.put("username", userEntity.getLogin());
        authorizationParameters.put("client_id", "web-client");
        authorizationParameters.put("grant", "password");
        return authorizationParameters;
    }

    private User preparePrincipal(UserEntity userEntity,
                                  Set<GrantedAuthority> authorities) {

        return new User(userEntity.getLogin(), "", true, true,
                true, true, authorities);
    }

    @Override
    public void link(UserEntity userEntity, OAuthClient client, SocialProfile userProfile) {

        oAuthAccountBindingService.link(userEntity, userProfile, client);
    }
}
