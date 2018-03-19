package com.omniesoft.commerce.support.service.categories.impl;

import com.omniesoft.commerce.persistence.projection.category.SubCategoryNamesPjn;
import com.omniesoft.commerce.persistence.repository.category.SubCategoryRepository;
import com.omniesoft.commerce.support.controller.categories.payload.SubCategoryPl;
import com.omniesoft.commerce.support.service.categories.CategoriesConverter;
import com.omniesoft.commerce.support.service.categories.SubCategoriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubCategoriesServiceImpl implements SubCategoriesService {

    private final SubCategoryRepository subCategoryRepo;
    private final CategoriesConverter converter;

    @Override
    @Transactional(readOnly = true)
    public Page<SubCategoryNamesPjn> subCategoriesPageByCategory(UUID categoryId, Pageable pageable) {
        return subCategoryRepo.findAllByCategoryId(categoryId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategoryPl getSubCategoryById(UUID subCategoryId) {
        SubCategoryPl result = converter.convert(subCategoryRepo.findByIdJoinCategory(subCategoryId));
        result.setOrganizationCount(subCategoryRepo.countOrganizationBySubCategoryId(subCategoryId));
        result.setServicesCount(subCategoryRepo.countServicesBySubCategoryId(subCategoryId));
        return result;
    }
}
