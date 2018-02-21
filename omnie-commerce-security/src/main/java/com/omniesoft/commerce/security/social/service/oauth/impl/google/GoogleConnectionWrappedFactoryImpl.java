package com.omniesoft.commerce.security.social.service.oauth.impl.google;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.security.social.service.oauth.OAuthConnectionWrappedFactory;
import lombok.AllArgsConstructor;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

@Service("googleConnectionWrappedFactory")
@AllArgsConstructor
public class GoogleConnectionWrappedFactoryImpl implements OAuthConnectionWrappedFactory {

    GoogleConnectionFactory connectionFactory;


    @Override
    public Connection<Google> connect(AccessGrant token) {
        return tryConnect(token);
    }

    @Override
    public Connection<Google> connect(AccessGrant token, String scopes) {
        connectionFactory.setScope(scopes);
        return tryConnect(token);
    }

    private Connection<Google> tryConnect(AccessGrant token) {

        Connection<Google> connection;
        try {
            connection = connectionFactory.createConnection(token);
            if (connection == null) {
                throw new UsefulException("Can`t get connection from facebook api. Service unavailable", SecurityModuleErrorCodes.SOCIAL_FACEBOOK_SIGN_IN_ERROR);
            }
        } catch (Exception e) {
            throw new UsefulException(e.getMessage(), SecurityModuleErrorCodes.SOCIAL_GOOGLE_SIGN_IN_ERROR);
        }
        return connection;
    }
}
