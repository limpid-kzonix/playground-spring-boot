package com.omniesoft.commerce.owner.controller.management;

import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.permissions.PersonnelManagementService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.admin.AdminRoleSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.omniesoft.commerce.persistence.entity.enums.AdminPermission.PERSONNEL_MANAGEMENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
@RestController
@Validated
@Api(value = "Personnel Management", tags = "Personnel Management API", description = "@omnie46")
@AllArgsConstructor
public class PersonnelManagementController extends AbstractOrganizationController {
    private final Logger log = LoggerFactory.getLogger(PersonnelManagementController.class);

    private final PersonnelManagementService personnelManagementService;
    private final OwnerAccessControlService accessControl;


    @GetMapping(path = "{organization-id}/roles", produces = APPLICATION_JSON_VALUE)
    public List<AdminRoleSummary> getRoles(@PathVariable("organization-id") UUID organizationId,
                                           @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, PERSONNEL_MANAGEMENT);

        return personnelManagementService.getRoles(organizationId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/roles", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addRole", notes = "permissions you can get from Permissions API")
    public ResponseMessage.Created addRole(@PathVariable("organization-id") UUID organizationId,
                                           @RequestParam("name") @Valid @NotEmpty String role,
                                           @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, PERSONNEL_MANAGEMENT);

        UUID id = personnelManagementService.saveRole(organizationId, role, user);

        return new ResponseMessage.Created(id);
    }

    @DeleteMapping(path = "{organization-id}/roles/{role-id}")
    public void deleteRole(@PathVariable("organization-id") UUID organizationId,
                           @PathVariable("role-id") UUID roleId,
                           @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByRoleId(user, roleId, PERSONNEL_MANAGEMENT);

        personnelManagementService.deleteRole(organizationId, roleId);
    }

    @PatchMapping(path = "{organization-id}/roles/{role-id}")
    public void updateRoleName(@PathVariable("organization-id") UUID organizationId,
                               @PathVariable("role-id") UUID roleId,
                               @RequestParam("name") @Valid @NotEmpty String name,
                               @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByRoleId(user, roleId, PERSONNEL_MANAGEMENT);

        personnelManagementService.updateRoleName(organizationId, roleId, name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/roles/{role-id}/permission", produces = APPLICATION_JSON_VALUE)
    public ResponseMessage.Created addPermissionToRole(@PathVariable("organization-id") UUID organizationId,
                                                       @PathVariable("role-id") UUID roleId,
                                                       @RequestParam("permission") AdminPermission permission,
                                                       @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByRoleId(user, roleId, PERSONNEL_MANAGEMENT);

        UUID id = personnelManagementService.addPermissionToRole(organizationId, roleId, permission);
        return new ResponseMessage.Created(id);
    }

    @DeleteMapping(path = "{organization-id}/roles/{role-id}/permission/{permission-id}")
    public void deleteRolePermission(@PathVariable("organization-id") UUID organizationId,
                                     @PathVariable("role-id") UUID roleId,
                                     @PathVariable("permission-id") UUID permissionId,
                                     @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByRoleId(user, roleId, PERSONNEL_MANAGEMENT);

        personnelManagementService.deleteRolePermission(organizationId, roleId, permissionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/roles/{role-id}/admin/{user-id}")
    public void addAdminToRole(@PathVariable("organization-id") UUID organizationId,
                               @PathVariable("role-id") UUID roleId,
                               @PathVariable("user-id") UUID userId,
                               @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByRoleId(user, roleId, PERSONNEL_MANAGEMENT);

        personnelManagementService.addUserToRole(organizationId, roleId, userId);
    }

    @DeleteMapping(path = "{organization-id}/roles/{role-id}/admin/{user-id}")
    public void deleteAdminFromRole(@PathVariable("organization-id") UUID organizationId,
                                    @PathVariable("role-id") UUID roleId,
                                    @PathVariable("user-id") UUID userId,
                                    @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByRoleId(user, roleId, PERSONNEL_MANAGEMENT);

        personnelManagementService.deleteUserFromRole(organizationId, roleId, userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/admin/{user-id}/services/{service-id}")
    public void addAdminToService(@PathVariable("organization-id") UUID organizationId,
                                  @PathVariable("user-id") UUID userId,
                                  @PathVariable("service-id") UUID serviceId,
                                  @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, PERSONNEL_MANAGEMENT);

        personnelManagementService.addAdminToService(organizationId, userId, serviceId);
    }

    @DeleteMapping(path = "{organization-id}/admin/{user-id}/services/{service-id}")
    public void deleteAdminFromService(@PathVariable("organization-id") UUID organizationId,
                                       @PathVariable("user-id") UUID userId,
                                       @PathVariable("service-id") UUID serviceId,
                                       @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, PERSONNEL_MANAGEMENT);

        personnelManagementService.deleteAdminFromService(organizationId, userId, serviceId);
    }
}
