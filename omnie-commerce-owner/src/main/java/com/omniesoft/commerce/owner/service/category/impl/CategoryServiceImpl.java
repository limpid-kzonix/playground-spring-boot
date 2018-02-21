package com.omniesoft.commerce.owner.service.category.impl;

import com.omniesoft.commerce.owner.service.category.CategoryService;
import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.repository.category.CategoryRepository;
import com.omniesoft.commerce.persistence.repository.category.SubCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;


    @Override
    public List<CategorySummary> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public List<SubCategorySummary> getSubCategoriesByCategoryId(UUID id) {
        return subCategoryRepository.findAllByCategoryId(id);
    }
}
