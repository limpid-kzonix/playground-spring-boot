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

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.account.UserOAuthSummary;
import com.omniesoft.commerce.persistence.repository.account.UserOAuthRepository;
import com.omniesoft.commerce.security.social.service.oauth.UserSocialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserSocialServiceImpl implements UserSocialService {

    private final UserOAuthRepository userOAuthRepository;

    @Override
    public List<UserOAuthSummary> fetchConnectedSocialAccount(UserEntity userEntity) {

        return userOAuthRepository.findAllByUserId(userEntity.getId());
    }
}
