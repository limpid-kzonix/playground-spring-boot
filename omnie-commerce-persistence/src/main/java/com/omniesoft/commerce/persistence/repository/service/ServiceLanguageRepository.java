package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.category.LanguageEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceLanguageEntity;
import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceLanguageSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceLanguageRepository extends PagingAndSortingRepository<ServiceLanguageEntity, UUID> {

    void deleteAllByIdServiceIdAndIdServiceOrganizationId(UUID service, UUID org);
    List<ServiceLanguageSummary> findAllByIdServiceIdAndIdServiceOrganizationId(UUID service, UUID org);
}
