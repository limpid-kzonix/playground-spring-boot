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

package com.omniesoft.commerce.user.controller.organization;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationReviewSummary;
import com.omniesoft.commerce.user.service.organization.ReviewService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Validated
@Api(value = "Review", tags = "Review Controller", description = "@limpid")
public class ReviewController extends AbstractOrganizationController {

    private final ReviewService reviewService;


    @GetMapping(path = "/{organization-id}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrganizationReviewSummary> getOrganizationReviews(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity) {

        return reviewService.getOrganizationReviews(id, pageable, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getServiceReviews(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("service-id") UUID service,
            @PathVariable("organization-id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {

        return reviewService.getServiceReviews(organization, service, pageable, userEntity);
    }

    @PostMapping(path = "/{organization-id}/reviews")
    public void postOrganizationReview(
            @PathVariable("organization-id") UUID orgId,
            @RequestParam("mark") @Min(0) @Max(5) Integer mark,
            @RequestParam("comment") @Size(min = 20, max = 300) String comment,
            @ApiIgnore UserEntity userEntity
    ) {

        reviewService.proposeOrganizationReview(orgId, mark, comment, userEntity);
    }

    @PostMapping(path = "/{organization-id}/services/{service-id}/reviews")
    public void postServiceReview(
            @PathVariable("organization-id") UUID orgId,
            @PathVariable("service-id") UUID serviceId,
            @RequestParam("mark") @Min(0) @Max(5) Integer mark,
            @RequestParam("comment") @Size(min = 20, max = 300) String comment,
            @ApiIgnore UserEntity userEntity
    ) {

        reviewService.proposeServiceReview(orgId, serviceId, mark, comment, userEntity);
    }

    @PutMapping(path = "/{organization-id}/reviews/{review-id}")
    public void updateOrganizationReview(
            @PathVariable("organization-id") UUID orgId,
            @PathVariable("review-id") UUID reviewId,
            @RequestParam("mark") @Min(0) @Max(5) Integer mark,
            @RequestParam("comment") @Size(min = 20, max = 300) String comment,
            @ApiIgnore UserEntity userEntity
    ) {

        reviewService.updateOrganizationReview(orgId, reviewId, mark, comment, userEntity);
    }

    @PutMapping(path = "/{organization-id}/services/{service-id}/reviews/{review-id}")
    public void updateServiceReview(
            @PathVariable("organization-id") UUID orgId,
            @PathVariable("review-id") UUID reviewId,
            @PathVariable("service-id") UUID serviceId,
            @RequestParam("mark") @Min(0) @Max(5) Integer mark,
            @RequestParam("comment") @Size(min = 20, max = 300) String comment,
            @ApiIgnore UserEntity userEntity
    ) {

        reviewService.updateServiceReview(orgId, serviceId, reviewId, mark, comment, userEntity);
    }
}
