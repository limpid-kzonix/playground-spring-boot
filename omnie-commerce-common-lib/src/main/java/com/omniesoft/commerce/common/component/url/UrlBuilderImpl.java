package com.omniesoft.commerce.common.component.url;

import java.net.URI;

public class UrlBuilderImpl implements UrlBuilder {
    private final String host;
    private final int port;

    public UrlBuilderImpl(String host, int port) {
        this.host = host;
        this.port = port;
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
        return URI.create(getHost() + "/omnie-user/api/account/confirmation/" + token);
    }

    @Override
    public URI changeEmailUrl(String token) {
        return URI.create(getHost() + "/omnie-user/api/account/email/change/" + token);
    }
}
