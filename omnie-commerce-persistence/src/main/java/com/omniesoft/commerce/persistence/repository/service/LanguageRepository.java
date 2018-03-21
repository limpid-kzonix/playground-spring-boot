package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.category.LanguageEntity;
import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface LanguageRepository extends PagingAndSortingRepository<LanguageEntity, UUID> {

    @Override
    List<LanguageEntity> findAll();

    @Query("select l from LanguageEntity l")
    List<LanguageSummary> findAvailableLanguages();
}
