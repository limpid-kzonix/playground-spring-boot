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
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceReviewEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationReviewSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceReviewSummary;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationReviewRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceReviewRepository;
import com.omniesoft.commerce.user.service.organization.ReviewService;
import com.omniesoft.commerce.user.service.util.event.review.organization.events.OnOrganizationReviewCreateEvent;
import com.omniesoft.commerce.user.service.util.event.review.service.events.OnServiceReviewCreateEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final OrderRepository orderRepository;

    private final ServiceReviewRepository serviceReviewRepository;

    private final OrganizationReviewRepository organizationReviewRepository;

    private final OrganizationRepository organizationRepository;

    private final ServiceRepository serviceRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Page<ServiceReviewSummary> getServiceReviews(UUID organizationId, UUID serviceId, Pageable pageable,
                                                        UserEntity userEntity) {

        return serviceReviewRepository.findAllByServiceIdAndServiceOrganizationIdAndAcceptStatusOrderByMarkDesc(serviceId, organizationId, true, pageable);
    }

    @Override
    public Page<OrganizationReviewSummary> getOrganizationReviews(UUID id, Pageable pageable, UserEntity userEntity) {

        return organizationReviewRepository.findAllByOrganizationIdAndAcceptStatusOrderByMarkDesc(id, true, pageable);
    }

    @Override
    @Transactional
    public void updateOrganizationReview(UUID organizationId, UUID reviewId, Integer mark, String comment,
                                         UserEntity userEntity) {

        OrganizationReviewEntity reviewEntity = editReview(organizationId, reviewId, userEntity);

        reviewEntity.setText(comment);
        reviewEntity.setMark(mark);

        organizationReviewRepository.save(reviewEntity);

    }

    private OrganizationReviewEntity editReview(UUID organizationId, UUID reviewId,
                                                UserEntity userEntity) {

        OrganizationReviewEntity reviewEntity = organizationReviewRepository
                .findByIdAndOrganizationIdAndUserAndAcceptStatus(reviewId, organizationId,
                        userEntity, false);

        if (reviewEntity == null) {
            throw new UsefulException(reviewId + " - review have already accepted. You can`t edit after " +
                    "accepting", UserModuleErrorCodes.NOT_ALLOWED_EDIT_REVIEW);
        }
        return reviewEntity;
    }

    @Override
    @Transactional
    public void updateServiceReview(UUID organizationId, UUID serviceId, UUID reviewId, Integer mark, String comment,
                                    UserEntity userEntity) {

        ServiceReviewEntity serviceReviewEntity = editServiceReview(serviceId, reviewId, userEntity);

        serviceReviewEntity.setText(comment);
        serviceReviewEntity.setMark(mark);
        serviceReviewRepository.save(serviceReviewEntity);
    }

    private ServiceReviewEntity editServiceReview(UUID serviceId, UUID reviewId, UserEntity userEntity) {

        ServiceReviewEntity serviceReviewEntity = serviceReviewRepository
                .findByIdAndServiceIdAndUserAndAcceptStatus(reviewId, serviceId, userEntity, false);

        if (serviceReviewEntity == null) {
            throw new UsefulException(reviewId + " : review have already accepted. You can`t edit after " +
                    "accepting", UserModuleErrorCodes.NOT_ALLOWED_EDIT_REVIEW);
        }
        return serviceReviewEntity;
    }

    @Override
    @Transactional
    public void proposeOrganizationReview(UUID organizationId, Integer mark, String reviewComment,
                                          UserEntity userEntity) {

        OrganizationEntity organizationEntity = organizationRepository.findOne(organizationId);

        checkReviewPossibilityByOrganization(organizationId, userEntity);

        OrganizationReviewEntity organizationReviewEntity = new OrganizationReviewEntity();
        organizationReviewEntity.setAcceptStatus(false);
        organizationReviewEntity.setCreateTime(LocalDateTime.now());
        organizationReviewEntity.setMark(mark);
        organizationReviewEntity.setOrganization(organizationEntity);
        organizationReviewEntity.setText(reviewComment);
        organizationReviewEntity.setUser(userEntity);
        organizationReviewEntity.setUpdateTime(LocalDateTime.now());

        OrganizationReviewEntity save = organizationReviewRepository.save(organizationReviewEntity);
        applicationEventPublisher.publishEvent(new OnOrganizationReviewCreateEvent(save));
    }


    @Override
    @Transactional
    public void proposeServiceReview(UUID organizationId, UUID serviceId, Integer mark, String reviewComment,
                                     UserEntity userEntity) {

        checkReviewPossibilityByService(serviceId, userEntity);

        ServiceEntity serviceEntity = serviceRepository.findOne(serviceId);


        ServiceReviewEntity serviceReviewEntity = new ServiceReviewEntity();
        serviceReviewEntity.setAcceptStatus(false);
        serviceReviewEntity.setCreateTime(LocalDateTime.now());
        serviceReviewEntity.setMark(mark);
        serviceReviewEntity.setText(reviewComment);
        serviceReviewEntity.setService(serviceEntity);
        serviceReviewEntity.setUser(userEntity);
        serviceReviewEntity.setUpdateTime(LocalDateTime.now());

        ServiceReviewEntity save = serviceReviewRepository.save(serviceReviewEntity);
        applicationEventPublisher.publishEvent(new OnServiceReviewCreateEvent(save));
    }

    private void checkReviewPossibilityByService(UUID serviceId, UserEntity userEntity) {

        checkIfReviewExistByService(serviceId, userEntity);
        checkIfOrderIsDoneByService(serviceId, userEntity);
    }

    private void checkReviewPossibilityByOrganization(UUID organizationId, UserEntity userEntity) {

        checkIfReviewExistByOrganization(organizationId, userEntity);
        checkIfOrderIsDoneByOrganization(organizationId, userEntity);
    }

    private void checkIfOrderIsDoneByService(UUID serviceId, UserEntity userEntity) {

        OrderEntity byServiceIdAndUserAndStatus = orderRepository
                .findByServiceIdAndUserAndStatus(serviceId, userEntity, OrderStatus.DONE);
        if (byServiceIdAndUserAndStatus == null) {
            throw new UsefulException("You can`t to leave a review (for service [" + serviceId + "]) ");
        }
    }

    private void checkIfReviewExistByService(UUID serviceId, UserEntity userEntity) {

        ServiceReviewEntity byServiceIdAndUser = serviceReviewRepository
                .findByUserAndServiceId(userEntity, serviceId);
        if (byServiceIdAndUser != null) {
            throw new UsefulException("You have already left a review (for service [" + serviceId
                    + "])", UserModuleErrorCodes.NOT_ALLOWED_CREATE_REVIEW);
        }
    }

    private void checkIfReviewExistByOrganization(UUID organizationId, UserEntity userEntity) {

        OrganizationReviewEntity byUserAndOrganizationId = organizationReviewRepository
                .findByUserAndOrganizationId(userEntity, organizationId);
        if (byUserAndOrganizationId != null) {
            throw new UsefulException("You have already left a review (for organization [" + organizationId
                    + "])", UserModuleErrorCodes.NOT_ALLOWED_CREATE_REVIEW);
        }
    }

    private void checkIfOrderIsDoneByOrganization(UUID organizationId, UserEntity userEntity) {

        OrderEntity byServiceOrganizationIdAndUserAndStatus = orderRepository
                .findByServiceOrganizationIdAndUserAndStatus(organizationId, userEntity, OrderStatus.DONE);

        if (byServiceOrganizationIdAndUserAndStatus == null) {
            throw new UsefulException(
                    "You can`t to leave a review (for organization [" + organizationId + "]) ",
                    UserModuleErrorCodes.NOT_ALLOWED_CREATE_REVIEW);
        }
    }
}
