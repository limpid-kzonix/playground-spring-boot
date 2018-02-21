package com.omniesoft.commerce.security.social.service.oauth;

import org.springframework.social.ApiBinding;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;

public interface OAuthConnectionWrappedFactory {

    Connection<? extends ApiBinding> connect(AccessGrant token);

    Connection<? extends ApiBinding> connect(AccessGrant token, String scopes);
}
