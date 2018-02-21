package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.common.util.Patterns;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.service.organization.OrganizationService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
@RestController
@Validated
@Api(value = "Organization Phone", tags = "Organization Phone API", description = "@omnie46")
@AllArgsConstructor
public class OrganizationPhoneController extends AbstractOrganizationController {

    private final OrganizationService organizationService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "{organization-id}/phones", produces = APPLICATION_JSON_VALUE)
    public List<OrganizationPhoneSummary> getPhones(@PathVariable("organization-id") UUID organizationId,
                                                    @ApiIgnore UserEntity user) {

        List<OrganizationPhoneSummary> phones = organizationService.getPhones(organizationId);
        return phones;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/phones", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addPhone", notes = "Return id of created Phone")
    public ResponseMessage.Created addPhone(@PathVariable("organization-id") UUID organizationId,
                                            @ApiParam
                                            @RequestParam("phone")
                                            @Pattern(regexp = Patterns.PHONE,
                                                    message = "Validation failed for phone number.")
                                                    String phone,
                                            @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        UUID phoneId = organizationService.addPhone(organizationId, phone);
        return new ResponseMessage.Created(phoneId);
    }

    @PatchMapping(path = "{organization-id}/phones/{phone-id}", produces = APPLICATION_JSON_VALUE)
    public void updatePhone(@PathVariable("organization-id") UUID organizationId,
                            @PathVariable("phone-id") UUID phoneId,
                            @ApiParam
                            @RequestParam("phone")
                            @Pattern(regexp = Patterns.PHONE,
                                    message = "Validation failed for phone number.")
                                    String phone,
                            @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        organizationService.updatePhone(phoneId, organizationId, phone);
    }

    @DeleteMapping(path = "{organization-id}/phones/{phone-id}", produces = APPLICATION_JSON_VALUE)
    public void deletePhone(@PathVariable("organization-id") UUID organizationId,
                            @ApiParam(required = true)
                            @PathVariable("phone-id") UUID phoneId,
                            @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        organizationService.deletePhone(organizationId, phoneId);
    }
}