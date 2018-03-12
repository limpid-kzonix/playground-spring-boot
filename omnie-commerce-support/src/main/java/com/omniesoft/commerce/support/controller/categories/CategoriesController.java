package com.omniesoft.commerce.support.controller.categories;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.category.CategoryNamesPjn;
import com.omniesoft.commerce.support.controller.categories.payload.CategoryPl;
import com.omniesoft.commerce.support.service.categories.CategoriesService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(value = "Categories", tags = "Categories API", description = "@omnie46")
@RequestMapping("v1/categoties")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<CategoryNamesPjn> getCategoriesPage(
            @ApiIgnore UserEntity user,
            @Valid PageableRequest pageableRequest, Pageable pageable) {

        return categoriesService.categoriesPage(pageable);
    }

    @GetMapping(path = "{category-id}",
            produces = APPLICATION_JSON_VALUE)
    public CategoryPl getCategoryInfo(
            @PathVariable("category-id") UUID categoryId,
            @ApiIgnore UserEntity user) {

        return categoriesService.getCategoryById(categoryId);
    }
}
