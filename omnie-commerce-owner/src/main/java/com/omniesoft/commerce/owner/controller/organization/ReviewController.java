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

package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.organization.ReviewService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationReviewSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceReviewSummary;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Api(tags = "Organization Reviews API", description = "@limpid")
public class ReviewController extends AbstractOrganizationController {

    private final ReviewService reviewService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "/{organization-id}/reviews")
    public Page<OrganizationReviewSummary> getOrganizationReview(
            @PathVariable("organization-id") UUID organizationId,
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity
    ) {

        return reviewService.getOrganizationReviews(organizationId, pageable, userEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/{organization-id}/reviews/{review-id}")
    public void confirmOrganizationReview(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("review-id") UUID reviewId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.REVIEW_MANAGEMENT);
        reviewService.confirmOrganizationReview(reviewId, organizationId, userEntity);
    }


    @GetMapping(path = "/{organization-id}/services/{service-id}/reviews")
    public Page<ServiceReviewSummary> getOrganizationReview(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity
    ) {
        return reviewService.getServiceReviews(organizationId, serviceId, pageable, userEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/{organization-id}/services/{service-id}reviews/{review-id}")
    public void confirmServiceReview(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @PathVariable("review-id") UUID reviewId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.REVIEW_MANAGEMENT);
        reviewService.confirmServiceReview(reviewId, serviceId, organizationId, userEntity);
    }
}
