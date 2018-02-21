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

package com.omniesoft.commerce.user.service.util.event.review;

import com.omniesoft.commerce.common.ws.notification.NotificationRestTemplate;
import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessage;
import com.omniesoft.commerce.common.ws.notification.payload.ReviewMessageFactory;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceReviewEntity;
import com.omniesoft.commerce.user.service.util.event.NotificationServiceAbstractClient;

public abstract class NotificationReviewAbstraction extends NotificationServiceAbstractClient {

    public NotificationReviewAbstraction(
            NotificationRestTemplate notificationRestTemplate) {

        super(notificationRestTemplate);
    }


    protected void send(ServiceReviewEntity serviceReviewEntity) {

        ReviewMessage compact = ReviewMessageFactory.compact(serviceReviewEntity);
        notificationRestTemplate.sendReviewNotification(compact);
    }

    protected void send(OrganizationReviewEntity organizationReviewEntity) {

        ReviewMessage compact = ReviewMessageFactory.compact(organizationReviewEntity);
        notificationRestTemplate.sendReviewNotification(compact);
    }
}
