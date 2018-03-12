package com.omniesoft.commerce.support.service.categories.impl;

import com.omniesoft.commerce.persistence.entity.category.CategoryEntity;
import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.support.controller.categories.payload.CategoryPl;
import com.omniesoft.commerce.support.controller.categories.payload.SubCategoryPl;
import com.omniesoft.commerce.support.service.categories.CategoriesConverter;
import org.springframework.stereotype.Service;

@Service
public class CategoriesConverterImpl implements CategoriesConverter {
    @Override
    public CategoryPl convert(CategoryEntity entity) {
        return new CategoryPl()
                .setId(entity.getId())
                .setCreateTime(entity.getCreateTime())
                .setLogoId(entity.getLogoId())
                .setUkrName(entity.getUkrName())
                .setEngName(entity.getEngName())
                .setRusName(entity.getRusName());
    }

    @Override
    public SubCategoryPl convert(SubCategoryEntity entity) {
        return new SubCategoryPl()
                .setId(entity.getId())
                .setCategoryId(entity.getCategory().getId())
                .setCreateTime(entity.getCreateTime())
                .setUkrName(entity.getUkrName())
                .setEngName(entity.getEngName())
                .setRusName(entity.getRusName());
    }
}
