package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.organization.NewsEntity;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 22.08.17
 */
public interface NewsRepositoryCustom {

    NewsEntity findFullData(UUID id);

    NewsEntity findWithServicesById(UUID id);

}
