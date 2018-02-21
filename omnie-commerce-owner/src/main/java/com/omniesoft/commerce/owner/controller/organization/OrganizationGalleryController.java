package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.service.organization.OrganizationService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
@RestController
@AllArgsConstructor
@Validated
@Api(value = "Organization Gallery", tags = "Organization Gallery API", description = "@omnie46")
public class OrganizationGalleryController extends AbstractOrganizationController {

    private final OrganizationService organizationService;

    private final OwnerAccessControlService ownerAccessControlService;


    @GetMapping(path = "{organization-id}/gallery", produces = APPLICATION_JSON_VALUE)
    public List<OrganizationGallerySummary> getGallery(@PathVariable("organization-id") UUID organizationId,
                                                       @ApiIgnore UserEntity user) {
        List<OrganizationGallerySummary> gallery = organizationService.getGallery(organizationId);
        return gallery;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/gallery/{image-id}", consumes = APPLICATION_JSON_VALUE, produces =
            APPLICATION_JSON_VALUE)
    public ResponseMessage.Created addToGallery(@PathVariable("organization-id") UUID organizationId,
                                                @PathVariable("image-id") String imageId,
                                                @ApiIgnore UserEntity user) {

        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        UUID uuid = organizationService.addToGallery(imageId, organizationId);
        return new ResponseMessage.Created(uuid);
    }


    @DeleteMapping(path = "{organization-id}/gallery/{id}", produces = APPLICATION_JSON_VALUE)
    public void deleteFromGallery(@PathVariable("organization-id") UUID organizationId,
                                  @PathVariable("id") UUID imageId,
                                  @ApiIgnore UserEntity user) {
        ownerAccessControlService.hasPermissionByOrganization(user, organizationId, AdminPermission.ORGANIZATION_MANAGEMENT);
        organizationService.deleteFromGallery(imageId, organizationId);
    }
}
