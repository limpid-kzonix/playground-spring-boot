package com.omniesoft.commerce.owner.controller.service;

import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.service.payload.ServiceTimingPayload;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.service.ServiceScopeTimingSettingService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Api(value = "Service", tags = "Service Settings API", description = "@limpid")
public class ServiceSettingController extends AbstractOrganizationController {

    private final ServiceScopeTimingSettingService serviceScopeTimingSettingService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "/{organization-id}/services/{service-id}/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceTimingPayload getServiceSetting(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeTimingSettingService.getServiceTimingSetting(organizationId, serviceId, userEntity);
    }

    @PutMapping(path = "/{organization-id}/services/{service-id}/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceTimingPayload setServiceSetting(
            @Valid @RequestBody ServiceTimingPayload serviceTimingPayload,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_SETTINGS);
        return serviceScopeTimingSettingService
                .setServiceTimingSetting(serviceTimingPayload, organizationId, serviceId, userEntity);
    }


}
