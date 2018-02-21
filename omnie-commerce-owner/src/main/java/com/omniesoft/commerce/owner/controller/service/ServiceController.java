package com.omniesoft.commerce.owner.controller.service;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.service.payload.LanguageListPayload;
import com.omniesoft.commerce.owner.controller.service.payload.ServicePayload;
import com.omniesoft.commerce.owner.controller.service.payload.ServiceStatePayload;
import com.omniesoft.commerce.owner.controller.service.payload.SubCategoriesListPayload;
import com.omniesoft.commerce.owner.service.category.CategoryService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.service.ServiceGalleryService;
import com.omniesoft.commerce.owner.service.service.ServiceScopeMainService;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowAdminExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
import com.omniesoft.commerce.persistence.projection.category.LanguageSummary;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@Api(value = "Service", tags = "Service API", description = "@limpid")
@AllArgsConstructor
public class ServiceController extends AbstractOrganizationController {

    private final ServiceScopeMainService serviceScopeMainService;
    private final ServiceGalleryService serviceGalleryService;
    private final CategoryService categoryService;
    private final OwnerAccessControlService ownerAccessControlService;


    @GetMapping(path = "/{organization-id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ServiceRowAdminExtendDto> getServices(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID organizationId,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeMainService.findOrganizationServices(organizationId, searchPattern, userEntity, pageable);
    }


    @PostMapping(
            path = "/{organization-id}/services",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ServicePayload createService(
            @PathVariable("organization-id") UUID organizationId,
            @RequestBody @Valid ServicePayload servicePayload,
            @ApiIgnore UserEntity userEntity) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CREATE_SERVICE);
        return serviceScopeMainService.save(servicePayload, organizationId, userEntity);

    }

    @PutMapping(
            path = "/{organization-id}/services/{service-id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ServicePayload updateService(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @RequestBody @Valid ServicePayload servicePayload,
            @ApiIgnore UserEntity userEntity) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_DESCRIPTION);
        return serviceScopeMainService.update(servicePayload, serviceId, organizationId, userEntity);

    }

    @GetMapping(path = "/{organization-id}/services/{service-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServicePayload getService(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeMainService.find(serviceId, organizationId, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceStatePayload getServiceState(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeMainService.findState(serviceId, organizationId, userEntity);
    }


    @GetMapping(path = "/service/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LanguageSummary> availableLanguages() {
        return serviceScopeMainService.findAvailableLanguages();
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/languages",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LanguageSummary> getServiceLanguages(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeMainService.findServiceLanguages(organizationId, serviceId, userEntity);
    }

    @PutMapping(path = "/{organization-id}/services/{service-id}/languages",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LanguageSummary> updateServiceLanguage(
            @Valid @RequestBody LanguageListPayload languagePayloadWrapper,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_DESCRIPTION);
        return serviceScopeMainService.saveServiceLanguages(languagePayloadWrapper.getLanguagePayloads(), serviceId, organizationId, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceGallerySummary> getServiceGallery(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceGalleryService.getServiceGallery(serviceId, organizationId, userEntity);
    }

    @PostMapping(
            path = "/{organization-id}/services/{service-id}/gallery",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseMessage.Created uploadImageToServiceGallery(
            @RequestParam("image_id") String imageId,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID service,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_GALLERY);
        UUID uuid = serviceGalleryService.setImageToServiceGallery(service, organizationId, imageId, userEntity);
        return new ResponseMessage.Created(uuid);
    }

    @DeleteMapping(path = "/{organization-id}/services/{service-id}/gallery/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteImageFromGallery(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @PathVariable("id") UUID imageId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_GALLERY);
        serviceGalleryService.deleteImageFromServiceGallery(serviceId, organizationId, imageId, userEntity);
    }

    @GetMapping(path = "/{organization-id}/service/{service-id}/categories/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategorySummary> getAllAvailableCategories(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/{organization-id}/service/{service-id}/categories",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubCategorySummary> getServiceCategories(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeMainService.findServiceCategories(organizationId, serviceId, userEntity);
    }

    @PutMapping(path = "/{organization-id}/services/{service-id}/categories",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<SubCategorySummary> updateServiceCategories(
            @Valid @RequestBody SubCategoriesListPayload subCategoriesWrapper,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_CATEGORIES);
        return serviceScopeMainService.saveServiceCategories(subCategoriesWrapper.getSubCategoryIds(), organizationId, serviceId,
                userEntity);
    }

    @PatchMapping(path = "/{organization-id}/services/{service-id}/freeze", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void freezeService(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @RequestParam(value = "reason", required = false) String reason,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SETTINGS);
        serviceScopeMainService.freezeService(organizationId, serviceId, userEntity, reason);
    }

    @PatchMapping(path = "/{organization-id}/services/{service-id}/unfreeze",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfreezeService(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SETTINGS);
        serviceScopeMainService.unfreezeService(organizationId, serviceId, userEntity);
    }

    @DeleteMapping(path = "/{organization-id}/services/{service-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SETTINGS);
        serviceScopeMainService.deleteService(organizationId, serviceId, userEntity);
    }


}
