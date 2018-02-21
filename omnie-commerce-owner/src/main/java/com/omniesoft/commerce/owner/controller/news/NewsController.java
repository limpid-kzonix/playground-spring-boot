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

package com.omniesoft.commerce.owner.controller.news;

import com.omniesoft.commerce.common.payload.news.NewsPayload;
import com.omniesoft.commerce.common.payload.news.NewsWithServicePayload;
import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.service.news.NewsService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
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
@Api(value = "News", tags = "News API", description = "@limpid")
@AllArgsConstructor
public class NewsController extends AbstractOrganizationController {

    private final NewsService newsService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "/{organization-id}/news/{news-id}")
    public NewsWithServicePayload getNewsById(
            @PathVariable("news-id") UUID newsId,
            @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity
    ) {
        return newsService.fetchNewsById(organizationId, newsId, userEntity);
    }

    @PostMapping(path = "/{organization-id}/news")
    public ResponseMessage.Created createNews(
            @PathVariable("organization-id") UUID organizationId,
            @Valid @RequestBody NewsPayload newsPayload,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.NEWS_MANAGEMENT);
        return new ResponseMessage.Created(newsService.createNews(organizationId, newsPayload, userEntity));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{organization-id}/news/{news-id}")
    public void updateNews(
            @PathVariable("news-id") UUID newsId,
            @PathVariable("organization-id") UUID organizationId,
            @Valid @RequestBody NewsPayload newsPayload,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.NEWS_MANAGEMENT);
        newsService.update(newsId, organizationId, newsPayload, userEntity);
    }

    @GetMapping(path = "/{organization-id}/news")
    public Page<NewsSummary> getNews(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity
    ) {
        return newsService.fetchOrganizationNews(organizationId, userEntity, pageable);
    }

    @GetMapping(path = "/{organization-id}/promotions")
    public Page<NewsSummary> getPromotions(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity
    ) {
        return newsService.fetchOrganizationPromotions(organizationId, userEntity, pageable);
    }


}
