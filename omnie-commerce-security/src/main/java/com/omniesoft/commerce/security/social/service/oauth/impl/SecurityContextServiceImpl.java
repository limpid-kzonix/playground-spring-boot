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

package com.omniesoft.commerce.security.social.service.oauth.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.security.social.service.oauth.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    private UserRepository userRepository;

    @Autowired
    public SecurityContextServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUser() {

        String userDetailsName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity byLogin = userRepository.findByLogin(userDetailsName);
        if (!byLogin.getEnabled()) {
            throw new UsefulException("User account is not activate. Confirm registration please",
                    UserModuleErrorCodes.USER_NOT_ACTIVE);
        }
        return byLogin;

    }
}
