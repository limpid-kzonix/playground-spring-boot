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

import com.omniesoft.commerce.mail.service.AccountMailService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.service.account.EmailConfirmationService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnRegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Slf4j
@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final AccountMailService accountMailService;
    private final EmailConfirmationService emailConfirmationService;

    @Autowired
    public RegistrationListener(
            AccountMailService accountMailService,
            EmailConfirmationService emailConfirmationService
    ) {
        this.accountMailService = accountMailService;
        this.emailConfirmationService = emailConfirmationService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.sendConfirmation(onRegistrationCompleteEvent);
    }

    private void sendConfirmation(OnRegistrationCompleteEvent event) {

        UserEntity userEntity = event.getUserEntity();
        String madeToken = RandomString.make(20).toLowerCase();
        emailConfirmationService.setEmailToken(userEntity, madeToken);
        try {
            accountMailService.sendRegistrationMessage(userEntity.getEmail(), madeToken);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
