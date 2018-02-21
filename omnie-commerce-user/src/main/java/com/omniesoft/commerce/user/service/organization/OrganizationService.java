package com.omniesoft.commerce.user.service.organization;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrganizationService {

    OrganizationRowExtendDto findById(UUID orgId, UserEntity userEntity);

    Page<OrganizationRowExtendDto> getOrganizations(String filter, UUID category, Pageable pageable, UserEntity userEntity);

    Map<DayOfWeek, List<OrganizationTimesheetDto>> getOrganizationTimeSheet(UUID id, Pageable pageable, UserEntity userEntity);

    Page<OrganizationGallerySummary> getOrganizationGallery(UUID id, Pageable pageable, UserEntity userEntity);

    Page<OrganizationPhoneSummary> getOrganizationPhones(UUID id, Pageable pageable, UserEntity userEntity);


}
