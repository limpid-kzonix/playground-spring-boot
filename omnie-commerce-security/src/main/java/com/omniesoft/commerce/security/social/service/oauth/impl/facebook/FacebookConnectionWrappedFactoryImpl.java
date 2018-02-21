package com.omniesoft.commerce.security.social.service.oauth.impl.facebook;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.security.social.service.oauth.OAuthConnectionWrappedFactory;
import lombok.AllArgsConstructor;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

@Service("facebookConnectionWrappedFactory")
@AllArgsConstructor
public class FacebookConnectionWrappedFactoryImpl implements OAuthConnectionWrappedFactory {

    FacebookConnectionFactory connectionFactory;


    @Override
    public Connection<Facebook> connect(AccessGrant token) {
        return tryConnect(token);
    }

    @Override
    public Connection<Facebook> connect(AccessGrant token, String scopes) {
        connectionFactory.setScope(scopes);
        return tryConnect(token);
    }

    private Connection<Facebook> tryConnect(AccessGrant token) {

        Connection<Facebook> connection;
        try {
            connection = connectionFactory.createConnection(token);
            if (connection == null) {
                throw new UsefulException("Can`t get connection from facebook api. Service unavailable", SecurityModuleErrorCodes.SOCIAL_FACEBOOK_SIGN_IN_ERROR);
            }
        } catch (Exception e) {
            throw new UsefulException(e.getMessage(), SecurityModuleErrorCodes.SOCIAL_FACEBOOK_SIGN_IN_ERROR);
        }
        return connection;
    }
}
