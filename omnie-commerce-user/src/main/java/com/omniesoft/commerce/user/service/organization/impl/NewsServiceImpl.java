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

package com.omniesoft.commerce.user.service.organization.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.NewsExtendedSummary;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationFavoriteSummary;
import com.omniesoft.commerce.persistence.repository.organization.NewsRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationFavoriteRepository;
import com.omniesoft.commerce.user.service.organization.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final OrganizationFavoriteRepository organizationFavoriteRepository;

    @Override
    public Page<NewsSummary> fetchOrganizationNews(UserEntity userEntity, Pageable pageable) {

        List<UUID> userFavoriteOrganizations = getUserFavoriteOrganizations(userEntity);
        return newsRepository.findAllByOrganizationIdAndPromotionStatusForUser(false, userFavoriteOrganizations,
                LocalDateTime.now(), pageable);
    }


    @Override
    public Page<NewsSummary> fetchOrganizationPromotions(UserEntity userEntity,
                                                         Pageable pageable) {

        List<UUID> userFavoriteOrganizations = getUserFavoriteOrganizations(userEntity);
        return newsRepository.findAllByOrganizationIdAndPromotionStatusForUser(true, userFavoriteOrganizations,
                LocalDateTime.now(), pageable);
    }

    private List<UUID> getUserFavoriteOrganizations(UserEntity userEntity) {

        List<OrganizationFavoriteSummary> allByUser = organizationFavoriteRepository.findAllByUser(userEntity);

        List<UUID> collect = allByUser
                .stream().map(this::key)
                .collect(Collectors
                        .toList());
        if (collect.isEmpty())
            collect.add(UUID.randomUUID());
        return collect;

    }

    private UUID key(OrganizationFavoriteSummary organizationFavoriteEntity) {

        return organizationFavoriteEntity
                .getOrganization()
                .getId();
    }

    @Override
    public NewsExtendedSummary fetchOrganizationNewById(UUID newsId, UserEntity userEntity) {

        NewsExtendedSummary byIdAndPromotionStatusIsFalse = newsRepository.findByIdAndPromotionStatusIsFalse(newsId);
        chechNewsEntity(byIdAndPromotionStatusIsFalse);
        return byIdAndPromotionStatusIsFalse;
    }

    @Override
    public NewsExtendedSummary fetchOrganizationPromotionById(UUID promotionsId, UserEntity userEntity) {

        NewsExtendedSummary byIdAndPromotionStatusIsTrue = newsRepository
                .findByIdAndPromotionStatusIsTrue(promotionsId);
        chechNewsEntity(byIdAndPromotionStatusIsTrue);
        return byIdAndPromotionStatusIsTrue;
    }

    private void chechNewsEntity(NewsExtendedSummary byIdAndPromotionStatusIsTrue) {

        if (byIdAndPromotionStatusIsTrue == null) {
            throw new UsefulException(InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
    }
}
