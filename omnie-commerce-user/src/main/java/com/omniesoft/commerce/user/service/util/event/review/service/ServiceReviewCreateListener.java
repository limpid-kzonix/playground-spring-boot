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

package com.omniesoft.commerce.user.service.util.event.review.service;

import com.omniesoft.commerce.common.ws.notification.NotificationRestTemplate;
import com.omniesoft.commerce.user.service.util.event.review.NotificationReviewAbstraction;
import com.omniesoft.commerce.user.service.util.event.review.service.events.OnServiceReviewCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceReviewCreateListener extends NotificationReviewAbstraction
        implements ApplicationListener<OnServiceReviewCreateEvent> {

    public ServiceReviewCreateListener(
            NotificationRestTemplate notificationRestTemplate) {

        super(notificationRestTemplate);
    }

    @Override
    public void onApplicationEvent(OnServiceReviewCreateEvent event) {

        send(event.getLookupSource());
    }
}
