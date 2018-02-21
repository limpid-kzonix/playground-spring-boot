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
import com.omniesoft.commerce.user.service.util.event.account.events.OnChangePasswordEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
@Slf4j
public class PasswordChangeListener implements ApplicationListener<OnChangePasswordEvent> {

    private final AccountMailService accountMailService;

    @Autowired
    public PasswordChangeListener(AccountMailService accountMailService) {
        this.accountMailService = accountMailService;
    }

    @Override
    public void onApplicationEvent(OnChangePasswordEvent onChangePasswordEvent) {
        try {
            accountMailService.sendPasswordChangeNotify(onChangePasswordEvent.getEmail(), onChangePasswordEvent.getUserName());
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
