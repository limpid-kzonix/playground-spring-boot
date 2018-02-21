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

package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceReviewEntity;
import com.omniesoft.commerce.persistence.projection.service.ServiceReviewSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ServiceReviewRepository extends CrudRepository<ServiceReviewEntity, UUID> {

    Page<ServiceReviewSummary> findAllByServiceIdAndServiceOrganizationIdAndAcceptStatusOrderByMarkDesc(UUID id, UUID organizationId, Boolean status, Pageable
            pageable);

    ServiceReviewEntity findByUserAndServiceId(UserEntity userEntity, UUID serviceId);

    ServiceReviewEntity findByIdAndServiceIdAndUserAndAcceptStatus(UUID reviewId, UUID serviceId, UserEntity
            userEntity, Boolean status);

    Page<ServiceReviewSummary> findByServiceIdAndServiceOrganizationId(UUID serviceId, UUID organization, Pageable
            pageable);

    ServiceReviewEntity findByIdAndServiceId(UUID reviewId, UUID serviceId);

}
