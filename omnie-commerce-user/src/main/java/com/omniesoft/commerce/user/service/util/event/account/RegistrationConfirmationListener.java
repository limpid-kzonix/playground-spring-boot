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
import com.omniesoft.commerce.persistence.entity.account.UserEmailVerificationEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserEmailVerificationRepository;
import com.omniesoft.commerce.user.service.util.event.account.events.OnRegistrationCompleteEvent;
import com.omniesoft.commerce.user.service.util.event.account.events.OnReplyRegistrationConfirmationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Slf4j
@Service
public class RegistrationConfirmationListener implements ApplicationListener<OnReplyRegistrationConfirmationEvent> {

    private final AccountMailService accountMailService;
    private final UserEmailVerificationRepository emailVerificationRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public RegistrationConfirmationListener(
            AccountMailService accountMailService,
            UserEmailVerificationRepository emailVerificationRepository, ApplicationEventPublisher eventPublisher) {
        this.accountMailService = accountMailService;
        this.emailVerificationRepository = emailVerificationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onApplicationEvent(OnReplyRegistrationConfirmationEvent onReplyRegistrationConfirmationEvent) {
        this.sendConfirmation(onReplyRegistrationConfirmationEvent);
    }

    private void sendConfirmation(OnReplyRegistrationConfirmationEvent event) {

        UserEntity userEntity = event.getUserEntity();
        UserEmailVerificationEntity firstByUserEmail = emailVerificationRepository.findFirstByUserEmail(userEntity.getEmail());
        if (firstByUserEmail == null) {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEntity));
            return;
        }

        try {
            accountMailService.sendRegistrationMessage(userEntity.getEmail(), firstByUserEmail.getToken());
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
