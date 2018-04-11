package com.omniesoft.commerce.common.component.url;

import java.net.URI;

public interface UrlBuilder {
    URI confirmAccountUrl(String token);

    URI changeEmailUrl(String token);
}
