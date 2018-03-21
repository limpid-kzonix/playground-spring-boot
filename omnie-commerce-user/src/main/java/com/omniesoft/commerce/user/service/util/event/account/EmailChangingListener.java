/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.user.service.util.event.account;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.mail.service.AccountMailService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.service.account.EmailConfirmationService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnEmailChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Slf4j
@Service
public class EmailChangingListener implements ApplicationListener<OnEmailChangeEvent> {

    private final AccountMailService accountMailService;

    @Autowired
    public EmailChangingListener(
            AccountMailService accountMailService,
            EmailConfirmationService emailConfirmationService
    ) {
        this.accountMailService = accountMailService;
    }

    @Override
    public void onApplicationEvent(OnEmailChangeEvent onEmailChangeEvent) {
        this.sendConfirmation(onEmailChangeEvent);
    }

    private void sendConfirmation(OnEmailChangeEvent event) {

        UserEntity userEntity = event.getUserEntity();
        String newEmail = event.getNewEmail();
        if (userEntity.getEmail().equalsIgnoreCase(newEmail)) {
            throw new UsefulException("You can not change the email address on the same").withCode(UserModuleErrorCodes.NOT_ALLOWED_CHANGE_OPERATION);
        }
        try {
            accountMailService.sendChangeEmailMessage(newEmail, userEntity.getEmail(), event.getToken());
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
