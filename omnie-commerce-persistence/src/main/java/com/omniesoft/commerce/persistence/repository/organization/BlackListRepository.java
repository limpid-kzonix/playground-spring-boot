/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced, distributed, or transmitted in
 * any form or by any means, including photocopying, recording, or other electronic or mechanical methods,
 * without the prior written permission of the publisher, except in the case of brief quotations embodied
 * in critical reviews and certain other noncommercial uses permitted by copyright law. For permission requests,
 * write to the publisher, addressed “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.organization.BlackListEntity;
import com.omniesoft.commerce.persistence.projection.organization.BlackListSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BlackListRepository extends PagingAndSortingRepository<BlackListEntity, UUID> {

    void deleteByOrganizationIdAndUserId(UUID organization, UUID userId);

    @Query(value = "" +
            "select bl from BlackListEntity bl " +
            "left join fetch bl.user user " +
            "left join fetch user.profile prf " +
            "left join fetch bl.organization org " +
            "where org.id = :orgId and concat(prf.firstName, ' ', prf.lastName) like concat('%',:pattern,'%') ",
            countQuery = "" +
                    "select bl from BlackListEntity bl " +
                    "left join  bl.user user " +
                    "left join  user.profile prf " +
                    "left join bl.organization org " +
                    "where org.id = :orgId and concat(prf.firstName, ' ', prf.lastName) like concat('%',:pattern,'%') ")
    Page<BlackListSummary> findByUserProfileFirstNameAndOrganizationId(@Param("pattern") String searchPattern,
                                                                       @Param("orgId") UUID orgId, Pageable pageable);
}
