package com.omniesoft.commerce.user.config.resolver.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.config.resolver.UserEntityResolver;
import com.omniesoft.commerce.user.service.security.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Service
public class UserEntityResolverImpl implements UserEntityResolver {

    private final SecurityContextService securityContextService;

    @Autowired
    public UserEntityResolverImpl(SecurityContextService securityContextService) {
        this.securityContextService = securityContextService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (null == securityContextService.getUser()) {
            throw new UnauthorizedUserException("UserEntity isn`t exist in security context.");
        }
        return securityContextService.getUser();
    }
}
