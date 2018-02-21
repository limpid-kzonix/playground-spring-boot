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

package com.omniesoft.commerce.user.service.util.event.review.organization.events;

import com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity;

public class OnOrganizationReviewUpdateEvent extends OrganizationReviewAbstractEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OnOrganizationReviewUpdateEvent(
            OrganizationReviewEntity source) {

        super(source);
    }
}
