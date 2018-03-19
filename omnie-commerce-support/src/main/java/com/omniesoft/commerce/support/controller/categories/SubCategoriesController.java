package com.omniesoft.commerce.support.controller.categories;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.category.SubCategoryNamesPjn;
import com.omniesoft.commerce.support.controller.categories.payload.SubCategoryPl;
import com.omniesoft.commerce.support.service.categories.SubCategoriesService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(value = "Sub Categories", tags = "Sub Categories API", description = "@omnie46")
public class SubCategoriesController {

    private final SubCategoriesService subCategoriesService;

    @GetMapping(path = "v1/categoties/{category-id}/sub-categoties",
            produces = APPLICATION_JSON_VALUE)
    public Page<SubCategoryNamesPjn> getCategoriesPageByCategory(
            @PathVariable("category-id") UUID categoryId,
            @ApiIgnore UserEntity user,
            @Valid PageableRequest pageableRequest, Pageable pageable) {

        return subCategoriesService.subCategoriesPageByCategory(categoryId, pageable);
    }

    @GetMapping(path = "v1/categoties/{category-id}/sub-categoties/{sub-category-id}",
            produces = APPLICATION_JSON_VALUE)
    public SubCategoryPl getCategoryInfo(
            @PathVariable("category-id") UUID categoryId,
            @PathVariable("sub-category-id") UUID subCategoryId,
            @ApiIgnore UserEntity user) {

        return subCategoriesService.getSubCategoryById(subCategoryId);
    }
}
