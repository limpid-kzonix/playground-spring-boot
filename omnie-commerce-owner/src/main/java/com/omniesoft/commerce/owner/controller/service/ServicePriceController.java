package com.omniesoft.commerce.owner.controller.service;

import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.service.payload.ServicePriceListPayload;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.service.ServiceScopeTimeSheetService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Api(value = "Service", tags = "Service Price API", description = "@limpid")
public class ServicePriceController extends AbstractOrganizationController {

    private final ServiceScopeTimeSheetService serviceScopeTimeSheetService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "/{organization-id}/service/{service-id}/timesheet")
    public Map<DayOfWeek, List<ServicePricePayload>> getTimeSheet(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeTimeSheetService.getServiceTimeSheet(serviceId, organizationId, userEntity);
    }

    @PutMapping(path = "/{organization-id}/service/{service-id}/timesheet/{day}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePriceForDay(
            @Valid @RequestBody ServicePriceListPayload servicePricePayload,
            @PathVariable("day") DayOfWeek day,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_PRICE);
        serviceScopeTimeSheetService.mergeDaysPrices(day, servicePricePayload.getServicePricePayload(), serviceId, organizationId, userEntity);
    }

    @DeleteMapping(path = "/{organization-id}/service/{service-id}/timesheet/{day}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deletePriceForDay(
            @PathVariable("day") DayOfWeek day,
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.EDIT_SERVICE_PRICE);
        serviceScopeTimeSheetService.deleteDaysPrices(day, serviceId, organizationId, userEntity);
    }
}
