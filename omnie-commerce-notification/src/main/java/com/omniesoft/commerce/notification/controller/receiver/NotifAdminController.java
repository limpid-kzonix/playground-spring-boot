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

package com.omniesoft.commerce.notification.controller.receiver;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;
import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import com.omniesoft.commerce.notification.event.OrderNotifEvent;
import com.omniesoft.commerce.notification.event.scope.UserEventScope;
import com.omniesoft.commerce.notification.util.event.admin.events.OnConversationAdminNotifyEvent;
import com.omniesoft.commerce.notification.util.event.admin.events.OnReviewAdminNotifyEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static com.omniesoft.commerce.common.Constants.WS.Owner.NOTIFICATION;

@Slf4j
@RestController
@AllArgsConstructor
public class NotifAdminController extends AbstractNotifController {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(path = "/admin/order")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void orderNotification(Principal emitter,
                                  @RequestBody NotifMessage<OrderNotifPl> message) {

        log.info(message.toString());
        UserEventScope scope = UserEventScope.of(emitter, NOTIFICATION, message.getContent().getOrgId());
        eventPublisher.publishEvent(new OrderNotifEvent(message, scope));

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
