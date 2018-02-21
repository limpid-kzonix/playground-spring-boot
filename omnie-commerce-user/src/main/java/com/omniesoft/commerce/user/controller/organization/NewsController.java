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
import com.omniesoft.commerce.persistence.projection.organization.NewsExtendedSummary;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
import com.omniesoft.commerce.user.service.organization.NewsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Api(value = "News", tags = "News API", description = "@limpid")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping(path = "/news")
    public Page<NewsSummary> getNews(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity
    ) {
        return newsService.fetchOrganizationNews(userEntity, pageable);
    }

    @GetMapping(path = "/promotions")
    public Page<NewsSummary> getPromotions(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity
    ) {
        return newsService.fetchOrganizationPromotions(userEntity, pageable);
    }

    @GetMapping(path = "/news/{news-id}")
    public NewsExtendedSummary getNewsById(
            @ApiIgnore UserEntity userEntity,
            @PathVariable("news-id") UUID newsId
    ) {
        return newsService.fetchOrganizationNewById(newsId, userEntity);
    }

    @GetMapping(path = "/promotions/{promotions-id}")
    public NewsExtendedSummary getPromotionById(
            @ApiIgnore UserEntity userEntity,
            @PathVariable("news-id") UUID newsId
    ) {
        return newsService.fetchOrganizationPromotionById(newsId, userEntity);
    }

}
