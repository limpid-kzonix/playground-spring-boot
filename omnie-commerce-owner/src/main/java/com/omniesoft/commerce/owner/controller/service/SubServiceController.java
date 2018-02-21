package com.omniesoft.commerce.owner.controller.service;

import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.service.SubServiceScopeService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Api(value = "Service", tags = "Sub-service  API", description = "@limpid")
public class SubServiceController extends AbstractOrganizationController {

    private final SubServiceScopeService subServiceScopeService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "/{organization-id}/services/{service-id}/sub_services")
    public List<SubServicePayload> getSubServices(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID service,
            @ApiIgnore UserEntity userEntity
    ) {

        return subServiceScopeService
                .findSubServiceByService(organizationId, service, userEntity);
    }

    @PostMapping(path = "/{organization-id}/services/{service-id}/sub_services")
    public SubServicePayload postSubService(
            @Valid @RequestBody SubServicePayload subServicePayload,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID service,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SUBSERVICE);
        return subServiceScopeService.save(subServicePayload, organizationId, service, userEntity);
    }

    @PutMapping(path = "/{organization-id}/services/{service-id}/sub_services/{sub-service-id}")
    public void updateSubService(
            @Valid @RequestBody SubServicePayload subServicePayload,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID service,
            @PathVariable("sub-service-id") UUID subService,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SUBSERVICE);
        subServiceScopeService.update(subServicePayload, organizationId, service, subService, userEntity);
    }

    @DeleteMapping(path = "/{organization-id}/services/{service-id}/sub_services/{sub-service-id}")
    public void deleteSubService(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID service,
            @PathVariable("sub-service-id") UUID subService,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SUBSERVICE);
        subServiceScopeService.deleteSubService(subService, service, organizationId, userEntity);
    }

}
