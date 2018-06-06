package com.omniesoft.commerce.common.component.url;

import com.omniesoft.commerce.common.Constants;

import java.net.URI;

public class UrlBuilderImpl implements UrlBuilder {
    private final String host;
    private final String notifHost;
    private final int port;


    private final URI ORDER_NOTIF_FOR_ADMIN;

    public UrlBuilderImpl(String host, int port, String notifHost) {
        this.host = host;
        this.port = port;
        this.notifHost = notifHost;

        this.ORDER_NOTIF_FOR_ADMIN = URI.create(notifHost + "/receiver/admin/order");
    }

    private String getHost() {
        if (port <= 0) {
            return host;
        } else {
            return host + ":" + port;
        }
    }

    @Override
    public URI confirmAccountUrl(String token) {
        return URI.create(getHost() + Constants.URL.User.ACCOUNT_CONFIRM + token);
    }

    @Override
    public URI changeEmailUrl(String token) {
        return URI.create(getHost() + Constants.URL.User.ACCOUNT_EMAIL_CHANGE + token);
    }

    @Override
    public URI orderNotifForAdmin() {
        return ORDER_NOTIF_FOR_ADMIN;
    }
}
