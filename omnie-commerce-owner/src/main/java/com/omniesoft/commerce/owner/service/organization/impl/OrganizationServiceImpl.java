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

import com.google.common.collect.Lists;
import com.omniesoft.commerce.common.converter.OrganizationTimeSheetConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.owner.controller.organization.payload.*;
import com.omniesoft.commerce.owner.converter.OrganizationConverter;
import com.omniesoft.commerce.owner.service.organization.OrganizationService;
import com.omniesoft.commerce.owner.service.organization.OrganizationTimeSheetValidator;
import com.omniesoft.commerce.owner.service.organization.OwnerService;
import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationGalleryEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationPhoneEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationTimeSheetEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationCardSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationGalleryRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationPhoneRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationTimeSheetRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 27.09.17
 */
@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;
    private final OrganizationGalleryRepository galleryRepository;
    private final OrganizationPhoneRepository phoneRepository;
    private final OrganizationTimeSheetRepository timeSheetRepository;
    private final OrganizationTimeSheetConverter timeSheetConverter;
    private final OrganizationTimeSheetValidator organizationTimeSheetValidator;
    private final OwnerService ownerService;
    private final OrganizationConverter converter;

    @Override
    @Transactional
    public CardOrganizationDto save(SaveOrganizationDto organization, UserEntity userEntity) {
        OrganizationEntity organizationEntity = new OrganizationEntity();

        organizationEntity.setName(organization.getName());
        organizationEntity.setLogoId(organization.getLogoId());
        organizationEntity.setPlaceId(organization.getPlaceId());
        organizationEntity.setEmail(organization.getEmail());
        organizationEntity.setLatitude(organization.getLatitude());
        organizationEntity.setLongitude(organization.getLongitude());
        organizationEntity.setFreezeStatus(false);
        organizationEntity.setDeleteStatus(false);

        organizationEntity.setPhones(
                Lists.newArrayList(
                        new OrganizationPhoneEntity(organizationEntity, organization.getPhone())
                )
        );

        ownerService.createOwnerAccount(userEntity);
        organizationEntity.setOwner(userEntity.getOwnerAccount());


        OrganizationEntity save = organizationRepository.save(organizationEntity);
        log.debug("Add organization for: " + userEntity.getLogin());
        log.debug(organization.toString());

        return converter.cardDto(save);
    }

    @Override
    public OrganizationEntity findById(UUID organizationId) {
        return organizationRepository.findOne(organizationId);
    }

    @Override
    public OrganizationDto findByIdFullProfile(UUID organizationId) {
        OrganizationEntity organizationEntity = organizationRepository
                .findByIdWithSettingPhonesTimesheetGallery(organizationId);

        return converter.organizationDto(organizationEntity);
    }

    @Override
    @Transactional
    public UUID addToGallery(String imageId, UUID organizationId) {
        OrganizationEntity organization = organizationRepository.findOne(organizationId);
        if (organization == null) {
            throw new UsefulException(organizationId.toString(), InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        OrganizationGalleryEntity organizationGalleryEntity = new OrganizationGalleryEntity(organization, imageId);
        OrganizationGalleryEntity save = galleryRepository.save(organizationGalleryEntity);
        return save.getId();

    }

    @Override
    @Transactional
    public void deleteFromGallery(UUID imageId, UUID organizationId) {
        galleryRepository.removeImages(imageId, organizationId);
    }

    @Override
    public void freezeOrganization(UUID organizationId, String reason) {
        organizationRepository.setFreezeStatus(organizationId, true, reason);
    }

    @Override
    public void unfreezeOrganization(UUID organizationId) {
        organizationRepository.setFreezeStatus(organizationId, false, null);
    }

    @Override
    public void setBackgroundImage(UUID organizationId, String imageId) {
        organizationRepository.updateBackgroundImage(organizationId, imageId);
    }

    @Override
    public void deleteBackgroundImage(UUID organizationId) {
        organizationRepository.updateBackgroundImage(organizationId, null);
    }

    @Override
    public void setLogoImage(UUID organizationId, String imageId) {
        organizationRepository.updateLogoImageId(organizationId, imageId);
    }

    @Override
    public void deleteLogoImage(UUID organizationId) {
        organizationRepository.updateLogoImageId(organizationId, null);
    }

    @Override
    public void setLocation(UUID organizationId, LocationDto location) {
        organizationRepository.updateLocation(
                organizationId,
                location.getPlaceId(),
                location.getLongitude(),
                location.getLatitude()
        );
    }

    @Override
    public void deletePhone(UUID organizationId, UUID phoneId) {
        phoneRepository.delete(organizationId, phoneId);
    }

    @Override
    public UUID addPhone(UUID organizationId, String phone) {
        OrganizationEntity organization = new OrganizationEntity();
        organization.setId(organizationId);

        OrganizationPhoneEntity organizationPhoneEntity = new OrganizationPhoneEntity(organization, phone);
        phoneRepository.save(organizationPhoneEntity);
        return organizationPhoneEntity.getId();

    }

    @Override
    public void updatePhone(UUID phoneId, UUID organizationId, String phone) {
        OrganizationEntity organization = new OrganizationEntity();
        organization.setId(organizationId);

        OrganizationPhoneEntity phoneEntity = new OrganizationPhoneEntity(phoneId, organization, phone);

        phoneRepository.save(phoneEntity);
    }

    @Override
    public List<OrganizationTimesheetDto> saveTimesheet(UUID organizationId, DayOfWeek day, SaveTimesheetListDto saveTimesheetListDto) {
        OrganizationEntity organization = new OrganizationEntity();
        organization.setId(organizationId);
        organizationTimeSheetValidator.validate(saveTimesheetListDto.getConfigForDay());
        List<OrganizationTimeSheetEntity> organizationTimeSheetEntities = saveOrganizationTimeSheet(day, saveTimesheetListDto, organization);
        return timeSheetConverter.toTimeSheet(organizationTimeSheetEntities).get(day);
    }

    private List<OrganizationTimeSheetEntity> saveOrganizationTimeSheet(DayOfWeek day, SaveTimesheetListDto saveTimesheetListDto, OrganizationEntity organization) {
        List<OrganizationTimeSheetEntity> organizationTimeSheetEntities = Lists.newArrayList();

        prepareOrgTimeSheetEntities(day, saveTimesheetListDto, organization, organizationTimeSheetEntities);
        Iterable<OrganizationTimeSheetEntity> save = timeSheetRepository.save(organizationTimeSheetEntities);
        return Lists.newArrayList(save);
    }

    private void prepareOrgTimeSheetEntities(DayOfWeek day, SaveTimesheetListDto saveTimesheetListDto, OrganizationEntity organization, List<OrganizationTimeSheetEntity> organizationTimeSheetEntities) {
        saveTimesheetListDto.getConfigForDay().forEach(e -> {
            OrganizationTimeSheetEntity timeSheet = new OrganizationTimeSheetEntity(
                    organization,
                    day,
                    e.getWorkStart(),
                    e.getWorkEnd(),
                    e.getBreakStart(),
                    e.getBreakEnd()
            );
            organizationTimeSheetEntities.add(timeSheet);
        });
    }

    @Override
    @Transactional
    public void updateTimesheet(UUID organizationId, DayOfWeek day, SaveTimesheetListDto saveTimesheetListDto) {
        OrganizationEntity organization = new OrganizationEntity();
        organization.setId(organizationId);

        organizationTimeSheetValidator.validate(saveTimesheetListDto.getConfigForDay());
        timeSheetRepository.deleteAllByOrganizationIdAndDay(organizationId, day);
        saveOrganizationTimeSheet(day, saveTimesheetListDto, organization);
    }

    @Override
    public void deleteTimesheet(UUID organizationId, DayOfWeek day) {
        timeSheetRepository.deleteAllByOrganizationIdAndDay(organizationId, day);
    }

    @Override
    public void updateDescription(UUID organizationId, OrganizationDescriptionDto description) {
        organizationRepository.updateDescription(organizationId,
                description.getTitle(),
                description.getDescription());
    }

    @Override
    public List<OrganizationCardSummary> findByOwner(OwnerEntity ownerAccount) {
        List<OrganizationCardSummary> organizationCards = organizationRepository.findAllByOwner(ownerAccount);
        return organizationCards;
    }

    @Override
    public List<OrganizationGallerySummary> getGallery(UUID organizationId) {

        List<OrganizationGallerySummary> gallery = galleryRepository.findAllByOrganizationId(organizationId);
        return gallery;
    }

    @Override
    public List<OrganizationPhoneSummary> getPhones(UUID organizationId) {
        return phoneRepository.findAllByOrganizationId(organizationId);
    }

    @Override
    public Map<DayOfWeek, List<OrganizationTimesheetDto>> getTimesheet(UUID organizationId) {
        List<OrganizationTimeSheetEntity> allByOrganizationId = timeSheetRepository.findAllByOrganizationId(organizationId);
        return timeSheetConverter.toTimeSheet(allByOrganizationId);
    }

    public Page<OrganizationCardSummary> findByOwnerOrAdmin(UserEntity user, Pageable pageable) {
        return organizationRepository.findAllByOwnerUserIdOrRolesAdminsContains(user.getId(), pageable);
    }

    @Override
    public void deleteOrganization(UUID id, String reason) {
        organizationRepository.setDeleteStatus(id, true, reason);

    }


}
