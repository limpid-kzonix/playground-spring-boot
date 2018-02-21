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

package com.omniesoft.commerce.owner.service.organization.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.owner.service.organization.ReviewService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceReviewEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationReviewSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceReviewSummary;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationReviewRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ServiceReviewRepository serviceReviewRepository;

    private final OrganizationReviewRepository organizationReviewRepository;


    @Override
    public Page<OrganizationReviewSummary> getOrganizationReviews(UUID organizationId, Pageable pageable,
                                                                  UserEntity userEntity) {

        return organizationReviewRepository.findAllByOrganizationId(organizationId, pageable);
    }

    @Override
    public void confirmOrganizationReview(UUID reviewId, UUID organizationId, UserEntity userEntity) {

        OrganizationReviewEntity byIdAndOrganizationId = organizationReviewRepository
                .findByIdAndOrganizationId(reviewId, organizationId);

        checkOrganizationReview(reviewId, organizationId, byIdAndOrganizationId);
        byIdAndOrganizationId.setAcceptStatus(true);
        organizationReviewRepository.save(byIdAndOrganizationId);
    }


    @Override
    public Page<ServiceReviewSummary> getServiceReviews(UUID organizationId, UUID serviceId, Pageable pageable,
                                                        UserEntity userEntity) {

        return serviceReviewRepository.findByServiceIdAndServiceOrganizationId(serviceId, organizationId, pageable);
    }

    @Override
    public void confirmServiceReview(UUID reviewId, UUID serviceId, UUID organizationId, UserEntity userEntity) {

        ServiceReviewEntity byIdAndServiceId = serviceReviewRepository.findByIdAndServiceId(reviewId, serviceId);
        checkServiceReview(reviewId, serviceId, byIdAndServiceId);
        byIdAndServiceId.setAcceptStatus(true);
        serviceReviewRepository.save(byIdAndServiceId);
    }

    private void checkServiceReview(UUID reviewId, UUID serviceId, ServiceReviewEntity byIdAndServiceId) {

        isServiceReviewExist(reviewId, serviceId, byIdAndServiceId);
        isServiceReviewUpdatable(byIdAndServiceId);
    }

    private void isServiceReviewUpdatable(ServiceReviewEntity byIdAndServiceId) {

        if (byIdAndServiceId.getAcceptStatus()) {
            throw new UsefulException("Service review is not updatable", OwnerModuleErrorCodes.ACTION_NOT_ALLOWED);
        }
    }

    private void isServiceReviewExist(UUID reviewId, UUID serviceId, ServiceReviewEntity byIdAndServiceId) {

        if (byIdAndServiceId == null) {
            throw new UsefulException("Service review with id [ " + reviewId + " ] does`t exist for " +
                    "service with id [ " + serviceId + " ]", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
    }


    private void checkOrganizationReview(UUID reviewId, UUID organizationId,
                                         OrganizationReviewEntity byIdAndOrganizationId) {

        isOrganizationReviewExist(reviewId, organizationId, byIdAndOrganizationId);
        isOrganizationReviewUpdatable(byIdAndOrganizationId);
    }

    private void isOrganizationReviewUpdatable(OrganizationReviewEntity byIdAndOrganizationId) {

        if (byIdAndOrganizationId.getAcceptStatus()) {
            throw new UsefulException("Organization review is not updatable", OwnerModuleErrorCodes.ACTION_NOT_ALLOWED);
        }
    }

    private void isOrganizationReviewExist(UUID reviewId, UUID organizationId,
                                           OrganizationReviewEntity byIdAndOrganizationId) {

        if (byIdAndOrganizationId == null) {
            throw new UsefulException("Organization review with id [ " + reviewId + " ] does`nt exist for " +
                    "organization " +
                    "with id [ " + organizationId + "]", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
    }
}
