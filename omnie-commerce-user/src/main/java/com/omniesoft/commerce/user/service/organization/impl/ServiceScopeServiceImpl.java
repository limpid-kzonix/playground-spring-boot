package com.omniesoft.commerce.user.service.organization.impl;

import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.converter.ServicePriceListConverter;
import com.omniesoft.commerce.common.converter.SubServicePriceConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.payload.service.OrganizationPriceList;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;
import com.omniesoft.commerce.persistence.repository.category.SubCategoryRepository;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.service.*;
import com.omniesoft.commerce.user.service.organization.ServiceScopeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceScopeServiceImpl implements ServiceScopeService {

    private final ServiceRepository serviceRepository;

    private final ServiceGalleryRepository serviceGalleryRepository;

    private final LanguageRepository languageRepository;

    private final SubCategoryRepository subCategoryRepository;

    private final ServicePriceConverter converter;

    private final ServiceTimingRepository serviceTimingRepository;

    private final ServicePriceRepository servicePriceRepository;

    private final SubServiceRepository subServiceRepository;

    private final SubServicePriceConverter subServicePriceConverter;

    private final ServicePriceListConverter servicePriceListConverter;

    private final OrderRepository orderRepository;


    @Override
    public ServiceRowUserExtendDto find(UUID organization, UUID service, UserEntity userEntity) {
        return serviceRepository.findWithUserServiceInfo(service, organization, userEntity);
    }

    @Override
    public Page<ServiceRowUserExtendDto> getServicesByCategoryAndFilter(String filter, UUID category, Pageable pageable,
                                                                        UserEntity userEntity) {
        return serviceRepository.findServicesByFilterAndCategoryAndUserEntity(filter.toLowerCase(), category, userEntity, pageable);
    }

    @Override
    public Page<ServiceRowUserExtendDto> getServicesByOrganizationIdAndFilter(String filter, UUID org,
                                                                              Pageable pageable, UserEntity userEntity) {

        return serviceRepository.findOrganizationServices(filter, org, userEntity, pageable);
    }

    @Override
    public List<ServiceGallerySummary> getServiceGallery(UUID service, UUID org, UserEntity userEntity) {
        return serviceGalleryRepository.findAllByServiceIdAndServiceOrganizationId(service, org);
    }

    @Override
    public List<LanguageSummary> findServiceLanguages(UUID org, UUID service, UserEntity userEntity) {
        return languageRepository.findAllByServiceIdAndServiceOrganizationId(service, org);
    }

    @Override
    public List<SubCategorySummary> findServiceCategories(UUID org, UUID service, UserEntity userEntity) {
        return subCategoryRepository.findAllByServiceIdAndServiceOrganizationId(service, org);
    }

    @Override
    public Map<DayOfWeek, List<ServicePricePayload>> getServiceTimeSheet(UUID service, UUID org, UserEntity userEntity) {

        LocalDateTime now = orderRepository.findLastDateOfOrderForService(service);

        List<ServicePriceEntity> servicePriceEntities = servicePriceRepository
                .findLastAvailableByServiceId(service, org, now);


        return converter.toTimeSheet(servicePriceEntities);
    }

    @Override
    public ServiceTimingEntity getServiceTimingSetting(UUID org, UUID service, UserEntity userEntity) {
        LocalDateTime now = LocalDateTime.now();
        ServiceTimingEntity byServiceId = serviceTimingRepository.findByServiceId(service, now);
        if (byServiceId == null) {
            throw new UsefulException("Setting for service with id [" + service + "] does not exist",
                    InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return byServiceId;
    }

    @Override
    public List<SubServicePayload> getSubServices(UUID org, UUID service, UserEntity userEntity) {

        List<SubServiceEntity> byIdAndOrganizationIdWithPrice = subServiceRepository
                .findByIdAndOrganizationIdWithPrice(service, org);

        List<SubServicePayload> convert = subServicePriceConverter
                .convert(byIdAndOrganizationIdWithPrice, LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        return convert == null ? new ArrayList<>() : convert;
    }

    @Override
    public List<OrganizationPriceList> getPriceList(UUID organization,
                                                    UserEntity userEntity) {

        List<ServiceEntity> organizationPriceList = serviceRepository
                .getOrganizationPriceList(organization);
        return servicePriceListConverter
                .toPriceList(organizationPriceList);
    }
}
