package com.omniesoft.commerce.common.ws;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface TokenRestTemplate {

    OAuth2AccessToken exchange();
}
