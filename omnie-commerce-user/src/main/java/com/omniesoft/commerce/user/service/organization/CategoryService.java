package com.omniesoft.commerce.user.service.organization;

import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategorySummary> getAllCategories();

    List<SubCategorySummary> getSubCategoriesByCategoryId(UUID id);
}
