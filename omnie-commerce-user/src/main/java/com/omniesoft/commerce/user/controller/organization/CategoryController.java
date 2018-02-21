package com.omniesoft.commerce.user.controller.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.user.service.organization.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/categories")
@Validated
@Api(value = "Categories", tags = "Categories Controller", description = "@limpid")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategorySummary> getCategories(@ApiIgnore UserEntity userEntity) {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/{id}/subcategories")
    public List<SubCategorySummary> getSubcategories(@PathVariable UUID id, @ApiIgnore UserEntity userEntity) {
        return categoryService.getSubCategoriesByCategoryId(id);
    }
}
