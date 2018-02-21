/*
 * Copyright (c) 2017.
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.security.config.resolver.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.security.config.resolver.UserEntityResolver;
import com.omniesoft.commerce.security.social.service.oauth.SecurityContextService;
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
