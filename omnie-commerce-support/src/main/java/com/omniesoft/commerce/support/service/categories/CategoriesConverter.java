package com.omniesoft.commerce.support.service.categories;

import com.omniesoft.commerce.persistence.entity.category.CategoryEntity;
import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.support.controller.categories.payload.CategoryPl;
import com.omniesoft.commerce.support.controller.categories.payload.SubCategoryPl;

public interface CategoriesConverter {

    CategoryPl convert(CategoryEntity entity);

    SubCategoryPl convert(SubCategoryEntity entity);

}
