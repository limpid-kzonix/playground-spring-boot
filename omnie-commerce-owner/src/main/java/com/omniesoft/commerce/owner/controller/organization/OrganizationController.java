package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.organization.payload.*;
import com.omniesoft.commerce.owner.service.organization.OrganizationService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationCardSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

import static com.omniesoft.commerce.persistence.entity.enums.AdminPermission.ORGANIZATION_MANAGEMENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 27.09.17
 */
@RestController
@AllArgsConstructor
@Validated
@Api(value = "Organization", tags = "Organization API", description = "@omnie46")
public class OrganizationController extends AbstractOrganizationController {

    private final OrganizationService organizationService;
    private final OwnerAccessControlService accessControl;


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<OrganizationCardSummary> getOrganizationsCards(
            @ApiIgnore UserEntity user,
            @Valid PageableRequest pageableRequest, Pageable pageable) {

        return organizationService.findByOwnerOrAdmin(user, pageable);
    }

    @GetMapping(path = "{organization-id}", produces = APPLICATION_JSON_VALUE)
    public OrganizationDto getOrganization(@PathVariable("organization-id") UUID organizationId,
                                           @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        OrganizationDto organization = organizationService.findByIdFullProfile(organizationId);
        return organization;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CardOrganizationDto addOrganization(@RequestBody @Valid SaveOrganizationDto organization,
                                               @ApiIgnore UserEntity user) {
        CardOrganizationDto organizationCard = organizationService.save(organization, user);
        return organizationCard;
    }


    @DeleteMapping(path = "{organization-id}/delete", produces = APPLICATION_JSON_VALUE)
    public void deleteOrganization(@PathVariable("organization-id") UUID organizationId,
                                   @ApiParam
                                   @RequestParam(value = "reason", required = false) String reason,
                                   @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.deleteOrganization(organizationId, reason);
    }

    @PatchMapping(path = "{organization-id}/freeze", produces = APPLICATION_JSON_VALUE)
    public void freezeOrganization(@PathVariable("organization-id") UUID organizationId,
                                   @ApiParam
                                   @RequestParam(value = "reason", required = false) String reason,
                                   @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.freezeOrganization(organizationId, reason);
    }

    @PatchMapping(path = "{organization-id}/unfreeze", produces = APPLICATION_JSON_VALUE)
    public void unfreezeOrganization(@PathVariable("organization-id") UUID organizationId,
                                     @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.unfreezeOrganization(organizationId);
    }

    @PutMapping(path = "{organization-id}/background/{image-id}", produces = APPLICATION_JSON_VALUE)
    public void updateBackgroundImage(@PathVariable("organization-id") UUID organizationId,
                                      @ApiParam
                                      @PathVariable(value = "image-id") String imageId,
                                      @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.setBackgroundImage(organizationId, imageId);
    }

    @PutMapping(path = "{organization-id}/description", produces = APPLICATION_JSON_VALUE)
    public void updateDescription(@PathVariable("organization-id") UUID organizationId,
                                  @ApiParam
                                  @RequestBody OrganizationDescriptionDto description,
                                  @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.updateDescription(organizationId, description);
    }

    @DeleteMapping(path = "{organization-id}/background", produces = APPLICATION_JSON_VALUE)
    public void deleteBackgroundImage(@PathVariable("organization-id") UUID organizationId,
                                      @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.deleteBackgroundImage(organizationId);
    }

    @PutMapping(path = "{organization-id}/logo/{image-id}", produces = APPLICATION_JSON_VALUE)
    public void updateLogoImage(@PathVariable("organization-id") UUID organizationId,
                                @ApiParam
                                @PathVariable(value = "image-id") String imageId,
                                @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.setLogoImage(organizationId, imageId);
    }

    @DeleteMapping(path = "{organization-id}/logo", produces = APPLICATION_JSON_VALUE)
    public void deleteLogoImage(@PathVariable("organization-id") UUID organizationId,
                                @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.deleteLogoImage(organizationId);
    }

    @PutMapping(path = "{organization-id}/location", produces = APPLICATION_JSON_VALUE)
    public void updateLocation(@PathVariable("organization-id") UUID organizationId,
                               @ApiParam
                               @RequestBody LocationDto location,
                               @ApiIgnore UserEntity user) {
        accessControl.hasPermissionByOrganization(user, organizationId, ORGANIZATION_MANAGEMENT);
        organizationService.setLocation(organizationId, location);
    }
}
