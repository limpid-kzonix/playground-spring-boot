package com.omniesoft.commerce.support.service.categories;

import com.omniesoft.commerce.persistence.projection.category.SubCategoryNamesPjn;
import com.omniesoft.commerce.support.controller.categories.payload.SubCategoryPl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubCategoriesService {

    Page<SubCategoryNamesPjn> subCategoriesPageByCategory(UUID categoryId, Pageable pageable);

    SubCategoryPl getSubCategoryById(UUID subCategoryId);

}
