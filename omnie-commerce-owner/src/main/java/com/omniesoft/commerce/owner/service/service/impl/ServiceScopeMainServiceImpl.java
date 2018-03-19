package com.omniesoft.commerce.owner.service.service.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.owner.controller.service.payload.LanguagePayload;
import com.omniesoft.commerce.owner.controller.service.payload.ServicePayload;
import com.omniesoft.commerce.owner.controller.service.payload.ServiceStatePayload;
import com.omniesoft.commerce.owner.service.service.ServiceScopeCrudService;
import com.omniesoft.commerce.owner.service.service.ServiceScopeMainService;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowAdminExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.category.LanguageEntity;
import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.repository.category.SubCategoryRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.service.LanguageRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceTimingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceScopeMainServiceImpl implements ServiceScopeMainService {

    private final ServiceRepository serviceRepository;

    private final ServiceTimingRepository serviceTimingRepository;

    private final OrganizationRepository organizationRepository;

    private final LanguageRepository languageRepository;

    private final ModelMapper modelMapper;

    private final SubCategoryRepository subCategoryRepository;

    private final ServiceScopeCrudService serviceScopeCrudService;


    @Override
    @Transactional
    public ServicePayload save(ServicePayload servicePayload, UUID org, UserEntity userEntity) {

        OrganizationEntity organizationEntity = getOrganizationEntity(org);

        ServiceEntity serviceEntity = prepareServiceEntityCreating(servicePayload, userEntity, organizationEntity);
        ServiceEntity savedService = serviceRepository.save(serviceEntity);

        ServiceTimingEntity serviceTimingEntity = prepareServiceTiming(userEntity, savedService);
        serviceTimingRepository.save(serviceTimingEntity);
        return modelMapper.map(serviceEntity, ServicePayload.class);
    }

    private OrganizationEntity getOrganizationEntity(UUID org) {

        OrganizationEntity one = organizationRepository.findOne(org);
        if (one == null) {
            throw new UsefulException(org.toString(), InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return one;
    }

    @Override
    public ServicePayload update(ServicePayload servicePayload, UUID service, UUID org, UserEntity userEntity) {


        ServiceEntity serviceWithOrganization = serviceRepository.findWithOrganizationAndSettings(service);
        ServiceEntity serviceEntity = prepareServiceEntityForUpdate(servicePayload, serviceWithOrganization);
        serviceEntity.setId(service);
        ServiceEntity saved = serviceRepository.save(serviceEntity);
        return modelMapper.map(saved, ServicePayload.class);
    }

    private ServiceEntity prepareServiceEntityForUpdate(ServicePayload servicePayload,
                                                        ServiceEntity serviceWithOrganization) {

        serviceWithOrganization.setName(servicePayload.getName());
        serviceWithOrganization.setLogoId(servicePayload.getLogoId());
        serviceWithOrganization.setDescription(servicePayload.getDescription());
        return serviceWithOrganization;
    }

    @Override
    public ServicePayload find(UUID service, UUID org, UserEntity userEntity) {

        ServiceEntity serviceEntity = serviceScopeCrudService.find(service);
        return modelMapper.map(serviceEntity, ServicePayload.class);
    }

    @Override
    public ServiceStatePayload findState(UUID service, UUID org, UserEntity userEntity) {

        ServiceEntity serviceEntity = serviceScopeCrudService.find(service);
        return modelMapper.map(serviceEntity, ServiceStatePayload.class);
    }

    private ServiceTimingEntity prepareServiceTiming(UserEntity userEntity,
                                                     ServiceEntity savedService) {

        ServiceTimingEntity serviceTimingEntity = new ServiceTimingEntity();
        serviceTimingEntity.setService(savedService);
        serviceTimingEntity.setUpdateByUser(userEntity);
        serviceTimingEntity = prepareDefaultServiceTiming(serviceTimingEntity);
        return serviceTimingEntity;
    }

    private ServiceTimingEntity prepareDefaultServiceTiming(ServiceTimingEntity serviceTimingEntity) {

        serviceTimingEntity.setDurationMillis(1000L * 60L * 60L);
        serviceTimingEntity.setMinDuration(false);
        serviceTimingEntity.setActiveFrom(LocalDateTime.now());
        serviceTimingEntity.setPauseMillis(0L);
        serviceTimingEntity.setSlotCount(1);
        serviceTimingEntity.setMaxDiscount(0.0);
        return serviceTimingEntity;
    }

    private ServiceEntity prepareServiceEntityCreating(ServicePayload servicePayload, UserEntity userEntity,
                                                       OrganizationEntity organizationEntity) {

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setLogoId(servicePayload.getLogoId());
        serviceEntity.setName(servicePayload.getName());
        serviceEntity.setDescription(servicePayload.getDescription());
        serviceEntity.setCreateByUser(userEntity);
        serviceEntity.setOrganization(organizationEntity);
        serviceEntity = getServiceDefaultStatus(serviceEntity);

        return serviceEntity;
    }

    private ServiceEntity getServiceDefaultStatus(ServiceEntity serviceEntity) {

        serviceEntity.setFreezeStatus(false);
        serviceEntity.setDeleteStatus(false);
        return serviceEntity;
    }

    private List<LanguageEntity> mapLanguages(List<LanguagePayload> languages) {

        List<LanguageEntity> all = languageRepository.findAll();

        return languages.stream()
                .flatMap(languagePayload ->
                        all.stream()
                                .filter(languageEntity ->
                                        languagePayload.getId().equals(languageEntity.getId())
                                )
                ).collect(Collectors.toList());
    }

    private List<SubCategoryEntity> mapSubCategories(List<UUID> categories) {

        List<SubCategoryEntity> all = subCategoryRepository.findAll();

        return categories.stream()
                .flatMap(uuid ->
                        all.stream()
                                .filter(categoryEntityPredicate ->
                                        uuid.equals(categoryEntityPredicate.getId())
                                )
                ).collect(Collectors.toList());
    }


    @Override
    public List<LanguageSummary> findAvailableLanguages() {

        return languageRepository.findAvailableLanguages();
    }

    @Override
    public List<LanguageSummary> saveServiceLanguages(List<LanguagePayload> languagePayloads, UUID service, UUID org,
                                                      UserEntity userEntity) {

        ServiceEntity one = serviceScopeCrudService.find(service);

        List<LanguageEntity> languageEntities = mapLanguages(languagePayloads);
        one.setLanguages(languageEntities);
        saveService(one);

        languageRepository.save(languageEntities);

        return this.findServiceLanguages(org, service, userEntity);
    }

    @Override
    public List<LanguageSummary> findServiceLanguages(UUID org, UUID service, UserEntity userEntity) {

        return languageRepository.findAllByServiceIdAndServiceOrganizationId(service, org);
    }

    @Override
    public List<ServiceRowAdminExtendDto> findOrganizationServices(UUID org, String searchPattern,
                                                                   UserEntity userEntity) {

        return serviceRepository.getServicesByOrganizationId(org);
    }

    @Override
    public List<SubCategorySummary> findServiceCategories(UUID org, UUID service, UserEntity userEntity) {

        return subCategoryRepository.findAllByServiceIdAndServiceOrganizationId(service, org);
    }

    @Override
    public List<SubCategorySummary> saveServiceCategories(List<UUID> subCategories, UUID org, UUID service, UserEntity
            userEntity) {

        ServiceEntity one = serviceScopeCrudService.find(service);
        List<SubCategoryEntity> subCategoryEntities = mapSubCategories(subCategories);
        one.setSubCategories(new HashSet<>(subCategoryEntities));
        saveService(one);
        return this.findServiceCategories(org, service, userEntity);
    }

    private void saveService(ServiceEntity one) {
        serviceRepository.save(one);
    }


    @Override
    public void freezeService(UUID org, UUID service, UserEntity userEntity, String reason) {

        serviceRepository.setFreezeStatus(service, true, reason);
    }

    @Override
    public void unfreezeService(UUID org, UUID service, UserEntity userEntity) {

        serviceRepository.setFreezeStatus(service, false, "");
    }

    @Override
    public void deleteService(UUID org, UUID service, UserEntity userEntity) {

        serviceRepository.setDeleteStatus(service, true);
    }

}
