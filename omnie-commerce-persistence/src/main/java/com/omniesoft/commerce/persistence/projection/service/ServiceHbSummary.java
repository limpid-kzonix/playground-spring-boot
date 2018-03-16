package com.omniesoft.commerce.persistence.projection.service;

import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;

import java.util.List;
import java.util.UUID;

public interface ServiceHbSummary {
    UUID getId();

    List<SubCategorySummary> getSubCategories();
}
