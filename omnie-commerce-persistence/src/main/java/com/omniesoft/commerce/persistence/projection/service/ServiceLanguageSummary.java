package com.omniesoft.commerce.persistence.projection.service;

import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
public interface ServiceLanguageSummary {
    @Value("#{target.getId().getLanguage()}")
    LanguageSummary getLanguage();


}
