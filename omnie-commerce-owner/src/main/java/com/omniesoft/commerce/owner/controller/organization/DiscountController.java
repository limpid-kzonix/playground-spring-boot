package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.organization.payload.DiscountDto;
import com.omniesoft.commerce.owner.controller.organization.payload.SaveDiscountDto;
import com.omniesoft.commerce.owner.service.organization.DiscountService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.DiscountSummary;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.omniesoft.commerce.persistence.entity.enums.AdminPermission.DISCOUNT_MANAGEMENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 23.10.17
 */
@RestController
@AllArgsConstructor
@Api(value = "Discount", tags = "Discount API", description = "@omnie46")
@Validated
public class DiscountController extends AbstractOrganizationController {

    private final DiscountService discountService;

    private final OwnerAccessControlService accessControl;


    @GetMapping(path = "{organization-id}/discounts", produces = APPLICATION_JSON_VALUE)
    public List<DiscountSummary> getDiscountsCards(@PathVariable("organization-id") UUID organizationId,
                                                   @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByOrganization(user, organizationId, DISCOUNT_MANAGEMENT);

        return discountService.findByOrganizationId(organizationId);
    }

    @GetMapping(path = "{organization-id}/discounts/{discount-id}", produces = APPLICATION_JSON_VALUE)
    public DiscountDto getDiscount(@PathVariable("organization-id") UUID organizationId,
                                   @PathVariable("discount-id") UUID discountId,
                                   @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);

        return discountService.findById(discountId, organizationId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{organization-id}/discounts", consumes = APPLICATION_JSON_VALUE)
    public ResponseMessage.Created saveDiscount(@PathVariable("organization-id") UUID organizationId,
                                                @RequestBody @Valid SaveDiscountDto discount,
                                                @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByOrganization(user, organizationId, DISCOUNT_MANAGEMENT);

        return new ResponseMessage.Created(discountService.save(organizationId, discount, user));
    }

    @DeleteMapping(path = "{organization-id}/discounts/{discount-id}")
    public void deleteDiscount(@PathVariable("organization-id") UUID organizationId,
                               @PathVariable("discount-id") UUID discountId,
                               @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);
        discountService.delete(discountId, organizationId);
    }

    @PutMapping(path = "{organization-id}/discounts/{discount-id}/services/{service-id}")
    public void addServiceToDiscount(@PathVariable("organization-id") UUID organizationId,
                                     @PathVariable("discount-id") UUID discountId,
                                     @PathVariable("service-id") UUID serviceId,
                                     @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);
        discountService.addService(discountId, organizationId, serviceId);
    }

    @DeleteMapping(path = "{organization-id}/discounts/{discount-id}/services/{service-id}")
    public void deleteServiceFromDiscount(@PathVariable("organization-id") UUID organizationId,
                                          @PathVariable("discount-id") UUID discountId,
                                          @PathVariable("service-id") UUID serviceId,
                                          @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);

        discountService.deleteService(discountId, organizationId, serviceId);
    }

    @PutMapping(path = "{organization-id}/discounts/{discount-id}/sub-services/{sub-service-id}")
    public void addSubServiceToDiscount(@PathVariable("organization-id") UUID organizationId,
                                        @PathVariable("discount-id") UUID discountId,
                                        @PathVariable("sub-service-id") UUID subServiceId,
                                        @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);

        discountService.addSubService(discountId, organizationId, subServiceId);
    }

    @DeleteMapping(path = "{organization-id}/discounts/{discount-id}/sub-services/{sub-service-id}")
    public void deleteSuberviceFromDiscount(@PathVariable("organization-id") UUID organizationId,
                                            @PathVariable("discount-id") UUID discountId,
                                            @PathVariable("sub-service-id") UUID subServiceId,
                                            @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);

        discountService.deleteSubService(discountId, organizationId, subServiceId);
    }

    @PutMapping(path = "{organization-id}/discounts/{discount-id}/user/{user-id}")
    public void addUserToDiscount(@PathVariable("organization-id") UUID organizationId,
                                  @PathVariable("discount-id") UUID discountId,
                                  @PathVariable("user-id") UUID userId,
                                  @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);

        discountService.addUser(discountId, organizationId, userId);
    }

    @DeleteMapping(path = "{organization-id}/discounts/{discount-id}/user/{user-id}")
    public void deleteUserFromDiscount(@PathVariable("organization-id") UUID organizationId,
                                       @PathVariable("discount-id") UUID discountId,
                                       @PathVariable("user-id") UUID userId,
                                       @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByDiscountId(user, discountId, DISCOUNT_MANAGEMENT);

        discountService.deleteUser(discountId, organizationId, userId);
    }
}
