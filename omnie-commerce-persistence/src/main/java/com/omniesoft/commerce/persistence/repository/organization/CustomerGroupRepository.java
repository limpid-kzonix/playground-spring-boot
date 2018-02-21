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

package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.dto.organization.CustomerDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.CustomerGroupEntity;
import com.omniesoft.commerce.persistence.projection.organization.CustomerGroupShortSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Transactional
public interface CustomerGroupRepository extends PagingAndSortingRepository<CustomerGroupEntity, UUID> {


    Page<CustomerGroupShortSummary> findAllByOrganizationId(UUID organizationId, Pageable pageable);

    List<CustomerGroupShortSummary> findAllByOrganizationIdAndUsersContains(UUID organization, UserEntity user);

    CustomerGroupEntity findByIdAndOrganizationId(UUID customerGroupId, UUID organizationId);

    void deleteByOrganizationIdAndId(UUID organizationId, UUID customerGroupId);

    @Query(value = "" +
            "select new com.omniesoft.commerce.persistence.dto.organization.CustomerDto (" +
            " user.id," +
            " user.login," +
            " user.email," +
            " p.firstName," +
            " p.lastName," +
            " p.imageId," +
            " count(o)," +
            " sum(o.totalPrice)," +
            " max(o.createTime)" +
            " " +
            ") from UserEntity user " +
            "left join user.profile p " +
            "left join user.orders o " +
            "left join o.service s " +
            "left join s.organization org " +
            "where org.id = :organization " +
            "and o.createTime between :startTime and :endTime " +
            "group by user.id, p.id, o.id, s.id ",
            countQuery = "select count( " +
                    "user.id" +
                    ") from UserEntity user " +
                    "left join user.profile p " +
                    "left join user.orders o " +
                    "left join o.service s " +
                    "left join s.organization org " +
                    "where org.id = :organization " +
                    "and o.createTime between :startTime and :endTime " +
                    "group by user.id, p.id, o.id, s.id"
    )
    Page<CustomerDto> findCustomersByOrganizationIdWithGroups(@Param("organization") UUID organizationId,
                                                              @Param("startTime") LocalDateTime from,
                                                              @Param("endTime") LocalDateTime to,
                                                              Pageable pageable);

    @Query(value = "" +
            "select new com.omniesoft.commerce.persistence.dto.organization.CustomerDto (" +
            " user.id," +
            " user.login," +
            " user.email," +
            " p.firstName," +
            " p.lastName," +
            " p.imageId," +
            " count(o)," +
            " sum(o.totalPrice)," +
            " max(o.createTime)" +
            " " +
            ") from UserEntity user " +
            "left join user.profile p " +
            "left join user.orders o " +
            "left join o.service s " +
            "left join s.organization org " +
            "where org.id = :organization " +
            "and s.id = :service " +
            "and o.createTime between :startTime and :endTime " +
            "group by user.id, p.id, o.id, s.id", countQuery = "" +
            "select count( " +
            "user.id" +
            ") from UserEntity user " +
            "left join user.profile p " +
            "left join user.orders o " +
            "left join o.service s " +
            "left join s.organization org " +
            "where org.id = :organization " +
            "and s.id = :service " +
            "and o.createTime between :startTime and :endTime " +
            "group by user.id, p.id, o.id, s.id"
    )
    Page<CustomerDto> findCustomersByOrganizationIdAndServiceIdWithGroups(
            @Param("organization") UUID organizationId,
            @Param("service") UUID serviceId,
            @Param("startTime") LocalDateTime from,
            @Param("endTime") LocalDateTime to,
            Pageable pageable);


    @Query(value = "" +
            "select new com.omniesoft.commerce.persistence.dto.organization.CustomerDto (" +
            " user.id," +
            " user.login," +
            " user.email," +
            " p.firstName," +
            " p.lastName," +
            " p.imageId," +
            " count(o)," +
            " sum(o.totalPrice)," +
            " max(o.createTime)" +
            "" +
            ") from UserEntity user  " +
            "left join user.profile p " +
            "left join user.orders o " +
            "left join o.service s " +
            "left join s.organization org " +
            "left join user.customerGroupEntities g " +
            "where org.id = :organization " +
            "and g.id = :groupId " +
            "and o.createTime between :startTime and :endTime " +
            "group by user.id, p.id, o.id, s.id " +
            "order by p.firstName ASC ", countQuery = "select count(" +
            " user.id " +
            ") from UserEntity user  " +
            "left join user.profile p " +
            "left join user.orders o " +
            "left join o.service s " +
            "left join s.organization org " +
            "left join user.customerGroupEntities g " +
            "where org.id = :organization " +
            "and g.id = :groupId " +
            "and o.createTime between :startTime and :endTime " +
            "group by user.id, p.id, o.id, s.id " +
            "order by p.firstName ASC "
    )
    Page<CustomerDto> findCustomersByOrganizationIdAndGroupIdWithGroups(
            @Param("organization") UUID organizationId,
            @Param("groupId") UUID groupId,
            @Param("startTime") LocalDateTime from,
            @Param("endTime") LocalDateTime to,
            Pageable pageable);


}
