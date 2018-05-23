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

package com.omniesoft.commerce.notification.controllers.receiver;

import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;
import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import com.omniesoft.commerce.common.ws.notification.payload.order.OrderMessage;
import com.omniesoft.commerce.notification.util.event.admin.events.OnConversationAdminNotifyEvent;
import com.omniesoft.commerce.notification.util.event.admin.events.OnOrderAdminNotifyEvent;
import com.omniesoft.commerce.notification.util.event.admin.events.OnReviewAdminNotifyEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
public class NotificationAdminReceiverController extends AbstractNotificationController {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(path = "/admin/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public void handleOrderNotification(@RequestBody OrderMessage orderMessage) {

        log.info(orderMessage.toString());

        eventPublisher.publishEvent(new OnOrderAdminNotifyEvent(orderMessage));

    }

    @PostMapping(path = "/admin/conversation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleConversationNotification(@RequestBody ConversationMessage conversationMessage) {
        log.info("Receive notification {}", conversationMessage.toString());
        eventPublisher.publishEvent(new OnConversationAdminNotifyEvent(conversationMessage));
    }

    @PostMapping(path = "/admin/review")
    public void handleReviewNotification(
            @Valid @RequestBody ReviewMessage reviewMessage) {
        eventPublisher.publishEvent(new OnReviewAdminNotifyEvent(reviewMessage));
    }


    @PostMapping(path = "/admin/general")
    public void handleGeneralNotification(
            @Valid @RequestBody ConversationMessage conversationMessage) {


    }

}
