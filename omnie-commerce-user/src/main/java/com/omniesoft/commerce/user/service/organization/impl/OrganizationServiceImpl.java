package com.omniesoft.commerce.user.service.organization.impl;

import com.omniesoft.commerce.common.converter.OrganizationTimeSheetConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.common.ws.statistic.impl.UserStatisticRestTemplate;
import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationTimeSheetEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationGalleryRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationPhoneRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationTimeSheetRepository;
import com.omniesoft.commerce.user.service.organization.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationTimeSheetRepository organizationTimeSheetRepository;
    private final OrganizationGalleryRepository organizationGalleryRepository;
    private final OrganizationPhoneRepository organizationPhoneRepository;
    private final OrganizationTimeSheetConverter organizationTimeSheetConverter;
    private final UserStatisticRestTemplate userStatisticRestTemplate;

    @Override
    public OrganizationRowExtendDto findById(UUID orgId, UserEntity userEntity) {
        OrganizationRowExtendDto withUserOrganizationInfo = organizationRepository
                .findWithUserOrganizationInfo(orgId, userEntity);
        if (withUserOrganizationInfo == null) {
            throw new UsefulException(orgId.toString(), InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return withUserOrganizationInfo;
    }

    @Override
    public Page<OrganizationRowExtendDto> getOrganizations(String filter, UUID category, Pageable pageable, UserEntity userEntity) {

        Page<OrganizationRowExtendDto> organizations = organizationRepository
                .getOrganizationsByFilterAndCategoryAndUserEntity(filter, category, userEntity, pageable);

        userStatisticRestTemplate.logUserOrganizationSearch(userEntity.getId(), filter);

        return organizations;
    }

    @Override
    public Map<DayOfWeek, List<OrganizationTimesheetDto>> getOrganizationTimeSheet(UUID id, Pageable pageable, UserEntity userEntity) {
        List<OrganizationTimeSheetEntity> allByOrganizationId = organizationTimeSheetRepository.findAllByOrganizationId(id);
        return organizationTimeSheetConverter.toTimeSheet(allByOrganizationId);
    }

    @Override
    public Page<OrganizationGallerySummary> getOrganizationGallery(UUID id, Pageable pageable, UserEntity userEntity) {
        return organizationGalleryRepository.findAllByOrganizationId(id, pageable);
    }

    @Override
    public Page<OrganizationPhoneSummary> getOrganizationPhones(UUID id, Pageable pageable, UserEntity userEntity) {
        return organizationPhoneRepository.findAllByOrganizationId(id, pageable);
    }


}
