package com.omniesoft.commerce.support.config.resolver;

import org.springframework.security.core.Authentication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.Optional;

public interface UserEntityResolver extends HandlerMethodArgumentResolver {

    Optional<Authentication> getAuthentication();

    Optional<String> getCurrentUserId();
}
