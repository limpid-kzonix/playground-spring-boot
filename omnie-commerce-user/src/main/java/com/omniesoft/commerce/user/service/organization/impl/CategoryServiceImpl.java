package com.omniesoft.commerce.user.service.organization.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.repository.category.CategoryRepository;
import com.omniesoft.commerce.persistence.repository.category.SubCategoryRepository;
import com.omniesoft.commerce.user.service.organization.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;
    private ModelMapper modelMapper;


    @Override
    public List<CategorySummary> getAllCategories() {

        List<CategorySummary> allCategories = categoryRepository.findAllCategories();

        return allCategories;
    }

    private void checkCollection(Collection<?> collection) {
        if (collection == null) {
            throw new UsefulException();
        }
    }

    @Override
    public List<SubCategorySummary> getSubCategoriesByCategoryId(UUID id) {

        List<SubCategorySummary> allByCategoryId = subCategoryRepository.findAllByCategoryId(id);
        return allByCategoryId;
    }
}
