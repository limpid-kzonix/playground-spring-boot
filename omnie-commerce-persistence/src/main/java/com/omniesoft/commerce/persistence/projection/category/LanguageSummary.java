package com.omniesoft.commerce.persistence.projection.category;

import com.omniesoft.commerce.persistence.entity.enums.LanguageCode;

import java.util.UUID;

public interface LanguageSummary {

    UUID getId();

    LanguageCode getName();

}
