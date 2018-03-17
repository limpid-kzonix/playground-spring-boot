package com.omniesoft.commerce.support.service.categories.impl;

import com.omniesoft.commerce.persistence.projection.category.CategoryNamesPjn;
import com.omniesoft.commerce.persistence.repository.category.CategoryRepository;
import com.omniesoft.commerce.support.controller.categories.payload.CategoryPl;
import com.omniesoft.commerce.support.service.categories.CategoriesConverter;
import com.omniesoft.commerce.support.service.categories.CategoriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoryRepository categoryRepo;
    private final CategoriesConverter converter;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryNamesPjn> categoriesPage(Pageable page) {
        return categoryRepo.findAllOnlyWithNames(page);

    }

    @Override
    @Transactional(readOnly = true)
    public CategoryPl getCategoryById(UUID categoryId) {
        CategoryPl result = converter.convert(categoryRepo.findOne(categoryId));
        result.setOrganizationCount(categoryRepo.countOrganizationByCategoryId(categoryId));
        result.setServicesCount(categoryRepo.countServicesByCategoryId(categoryId));
        return result;
    }
}
