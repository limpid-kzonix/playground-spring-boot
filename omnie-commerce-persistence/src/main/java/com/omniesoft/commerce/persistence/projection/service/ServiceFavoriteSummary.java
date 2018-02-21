package com.omniesoft.commerce.persistence.projection.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
public interface ServiceFavoriteSummary {
    @Value("#{target.getId().getService()}")
    ServiceSummary getService();


}
