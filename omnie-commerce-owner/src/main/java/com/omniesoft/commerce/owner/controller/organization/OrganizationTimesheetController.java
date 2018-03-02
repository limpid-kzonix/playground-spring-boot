package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.organization.payload.SaveTimesheetListDto;
import com.omniesoft.commerce.owner.service.organization.OrganizationService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
@RestController
@Validated
@Api(value = "Organization Timesheet", tags = "Organization Timesheet API", description = "@omnie46")
@AllArgsConstructor
public class OrganizationTimesheetController extends AbstractOrganizationController {


    private final OrganizationService organizationService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "{organization-id}/timesheet", produces = APPLICATION_JSON_VALUE)
    public Map<DayOfWeek, List<OrganizationTimesheetDto>> getTimesheet(@PathVariable("organization-id") UUID organizationId,
                                                                       @ApiIgnore UserEntity user) {

        return organizationService.getTimesheet(organizationId);
    }

    @PostMapping(path = "{organization-id}/timesheet/{day}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "addTimesheet", notes = "Return id of created Timesheet")
    public List<OrganizationTimesheetDto> addTimesheet(@PathVariable("organization-id") UUID organizationId,
                                                       @ApiParam
                                                       @RequestBody @Valid SaveTimesheetListDto timesheetDto,
                                                       @PathVariable("day") DayOfWeek day,
                                                       @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        return organizationService.saveTimesheet(organizationId, day, timesheetDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = "{organization-id}/timesheet/{day}", produces = APPLICATION_JSON_VALUE)
    public void updateTimesheet(@PathVariable("organization-id") UUID organizationId,
                                @ApiParam
                                @RequestBody @Valid SaveTimesheetListDto timesheetDto,
                                @PathVariable("day") DayOfWeek day,
                                @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        organizationService.updateTimesheet(organizationId, day, timesheetDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{organization-id}/timesheet/{day}", produces = APPLICATION_JSON_VALUE)
    public void deleteTimesheet(@PathVariable("organization-id") UUID organizationId,
                                @ApiParam(required = true)
                                @PathVariable("day") DayOfWeek day,
                                @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        organizationService.deleteTimesheet(organizationId, day);
    }
}
