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

package com.omniesoft.commerce.security.social.controller;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.account.UserOAuthSummary;
import com.omniesoft.commerce.security.social.service.oauth.UserSocialService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/social")
public class UserOAuthController {

    private UserSocialService userSocialService;

    @GetMapping("/connected")
    public List<UserOAuthSummary> fetchConnectedOAuthAccount(
            @ApiIgnore UserEntity userEntity
    ) {
        return userSocialService.fetchConnectedSocialAccount(userEntity);
    }
}
