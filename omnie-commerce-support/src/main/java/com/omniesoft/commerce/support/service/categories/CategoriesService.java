package com.omniesoft.commerce.support.service.categories;

import com.omniesoft.commerce.persistence.projection.category.CategoryNamesPjn;
import com.omniesoft.commerce.support.controller.categories.payload.CategoryPl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoriesService {
    Page<CategoryNamesPjn> categoriesPage(Pageable pageable);

    CategoryPl getCategoryById(UUID categoryId);
}
