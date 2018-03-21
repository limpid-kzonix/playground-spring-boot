package com.omniesoft.commerce.owner.service.service;

import com.omniesoft.commerce.owner.controller.service.payload.LanguagePayload;
import com.omniesoft.commerce.owner.controller.service.payload.ServicePayload;
import com.omniesoft.commerce.owner.controller.service.payload.ServiceStatePayload;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowAdminExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceLanguageSummary;

import java.util.List;
import java.util.UUID;

public interface ServiceScopeMainService {

    ServicePayload save(ServicePayload servicePayload, UUID org, UserEntity userEntity);

    ServicePayload update(ServicePayload servicePayload, UUID service, UUID org, UserEntity userEntity);

    ServicePayload find(UUID service, UUID org, UserEntity userEntity);

    ServiceStatePayload findState(UUID service, UUID org, UserEntity userEntity);

    List<ServiceRowAdminExtendDto> findOrganizationServices(UUID org, String searchPattern, UserEntity userEntity);

    List<LanguageSummary> findAvailableLanguages();

    List<ServiceLanguageSummary> saveServiceLanguages(List<LanguagePayload> languagePayloads, UUID service, UUID org,
                                                      UserEntity userEntity);

    List<ServiceLanguageSummary> findServiceLanguages(UUID org, UUID service, UserEntity userEntity);

    List<SubCategorySummary> findServiceCategories(UUID org, UUID service, UserEntity userEntity);

    List<SubCategorySummary> saveServiceCategories(List<UUID> subCategories, UUID org, UUID service, UserEntity
            userEntity);

    void freezeService(UUID org, UUID service, UserEntity userEntity, String reason);

    void unfreezeService(UUID org, UUID service, UserEntity userEntity);

    void deleteService(UUID org, UUID service, UserEntity userEntity);


}
