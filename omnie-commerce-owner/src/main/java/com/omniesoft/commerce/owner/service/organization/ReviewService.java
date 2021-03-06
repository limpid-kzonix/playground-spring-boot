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

package com.omniesoft.commerce.owner.service.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationReviewSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceReviewSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReviewService {

    Page<OrganizationReviewSummary> getOrganizationReviews(UUID organizationId, Pageable pageable,
                                                           UserEntity userEntity);

    void confirmOrganizationReview(UUID reviewId, UUID organizationId, UserEntity userEntity);

    Page<ServiceReviewSummary> getServiceReviews(UUID organizationId, UUID serviceId, Pageable pageable,
                                                 UserEntity userEntity);

    void confirmServiceReview(UUID reviewId, UUID serviceId, UUID organizationId, UserEntity userEntity);
}
