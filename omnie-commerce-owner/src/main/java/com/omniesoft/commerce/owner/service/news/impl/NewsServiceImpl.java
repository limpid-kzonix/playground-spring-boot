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

package com.omniesoft.commerce.owner.service.news.impl;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.common.payload.news.NewsPayload;
import com.omniesoft.commerce.common.payload.news.NewsWithServicePayload;
import com.omniesoft.commerce.owner.service.news.NewsService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.NewsEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
import com.omniesoft.commerce.persistence.repository.organization.NewsRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final OrganizationRepository organizationRepository;

    private final ModelMapper modelMapper;

    @Override
    public UUID createNews(UUID organizationId, NewsPayload newsPayload, UserEntity userEntity) {


        OrganizationEntity organizationEntity = organizationRepository.findOne(organizationId);


        NewsEntity newsEntity = new NewsEntity();

        newsEntity.setOrganization(organizationEntity);


        setNewsData(newsPayload, userEntity, organizationEntity, newsEntity);

        NewsEntity save = newsRepository.save(newsEntity);
        return save.getId();

    }

    @Override
    public NewsWithServicePayload fetchNewsById(UUID organizationId, UUID newsId, UserEntity userEntity) {

        NewsEntity withServicesById = newsRepository.findWithServicesById(newsId);
        NewsWithServicePayload map = modelMapper.map(withServicesById, NewsWithServicePayload.class);
        return map;
    }

    private List<ServiceEntity> getServiceFromNewsPayload(NewsPayload newsPayload,
                                                          OrganizationEntity organizationEntity) {

        List<ServiceEntity> services = organizationEntity.getServices();
        if (checkServiceFromPayload(newsPayload)) return Lists.newArrayList();
        return getCompatibleServiceEntities(newsPayload, services);

    }

    private boolean checkServiceFromPayload(NewsPayload newsPayload) {
        List<UUID> serviceUuids = newsPayload.getServices();
        if (serviceUuids == null) {
            return true;
        }
        return false;
    }

    private List<ServiceEntity> getCompatibleServiceEntities(NewsPayload newsPayload, List<ServiceEntity> services) {
        return services.stream().filter(
                serviceEntity -> newsPayload.getServices().contains(serviceEntity.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void update(UUID newsId, UUID organizationId, NewsPayload newsPayload, UserEntity userEntity) {

        OrganizationEntity organizationEntity = organizationRepository.findOne(organizationId);


        NewsEntity newsEntity = newsRepository.findOne(newsId);
        setNewsData(newsPayload, userEntity, organizationEntity, newsEntity);

        newsRepository.save(newsEntity);
    }

    private void setNewsData(NewsPayload newsPayload, UserEntity userEntity, OrganizationEntity organizationEntity,
                             NewsEntity newsEntity) {

        newsEntity.setCreateByUser(userEntity);
        newsEntity.setImageId(newsPayload.getImageId());
        newsEntity.setPostTime(newsPayload.getPostTime());
        newsEntity.setServices(getServiceFromNewsPayload(newsPayload, organizationEntity));
        newsEntity.setPromotionStatus(newsPayload.getPromotionStatus());
        newsEntity.setText(newsPayload.getText());
        newsEntity.setTitle(newsPayload.getTitle());
        newsEntity.setCreateTime(LocalDateTime.now());
    }

    @Override
    public Page<NewsSummary> fetchOrganizationNews(UUID organizationId, UserEntity userEntity, Pageable pageable) {

        return newsRepository
                .findAllByOrganizationIdAndPromotionStatus(organizationId, false, pageable);
    }

    @Override
    public Page<NewsSummary> fetchOrganizationPromotions(UUID organizationId, UserEntity userEntity, Pageable pageable) {

        return newsRepository
                .findAllByOrganizationIdAndPromotionStatus(organizationId, true, pageable);

    }
}
