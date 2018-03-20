package com.omniesoft.commerce.user.service.organization;

import com.omniesoft.commerce.common.payload.service.OrganizationPriceList;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceLanguageSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ServiceScopeService {

    ServiceRowUserExtendDto find(UUID organization, UUID service, UserEntity userEntity);

    Page<ServiceRowUserExtendDto> getServicesByCategoryAndFilter(String filter, UUID category, Pageable pageable, UserEntity userEntity);

    Page<ServiceRowUserExtendDto> getServicesByOrganizationIdAndFilter(String filter, UUID org, Pageable pageable, UserEntity userEntity);

    List<ServiceGallerySummary> getServiceGallery(UUID service, UUID org, UserEntity userEntity);

    List<ServiceLanguageSummary> findServiceLanguages(UUID org, UUID service, UserEntity userEntity);

    List<SubCategorySummary> findServiceCategories(UUID org, UUID service, UserEntity userEntity);

    Map<DayOfWeek, List<ServicePricePayload>> getServiceTimeSheet(UUID service, UUID org, UserEntity userEntity);

    ServiceTimingEntity getServiceTimingSetting(UUID org, UUID service,
                                                UserEntity userEntity);

    List<SubServicePayload> getSubServices(UUID org, UUID service, UserEntity userEntity);


    List<OrganizationPriceList> getPriceList(UUID organization, UserEntity userEntity);


}
