package com.omniesoft.commerce.owner.controller.user;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.owner.controller.user.payload.CustomerPayload;
import com.omniesoft.commerce.owner.controller.user.payload.SearchType;
import com.omniesoft.commerce.owner.service.user.CustomerService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 19.10.17
 */
@RestController
@RequestMapping(path = "user")
@Validated
@Api(value = "Customers Group Controller", tags = "Customers API", description = "@limpid")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(path = "/organizations/{organization-id}/customers/search", produces = APPLICATION_JSON_VALUE)
    public Page<AccountProfileDataSummary> findUsers(
            @PathVariable("organization-id") UUID organizationId,
            @RequestParam(value = "type", required = true, defaultValue = "EMAIL") SearchType type,
            @RequestParam("search") String searchPattern,
            @ApiIgnore UserEntity userEntity) {


        return customerService.findUsersByTypeAndPattern(organizationId, type, searchPattern, userEntity);

    }

    @GetMapping(path = "/organizations/{organization-id}/customers/{customer-id}", produces = APPLICATION_JSON_VALUE)
    public AccountProfileDataSummary fetchUserInfo(@PathVariable("organization-id") UUID organizationId,
                                                   @PathVariable("customer-id") UUID userId,
                                                   @ApiIgnore UserEntity userEntity) {

        return customerService.findCustomerInformation(userId, organizationId, userEntity);
    }

    @GetMapping(path = "/organizations/{organization-id}/customers")
    public Page<CustomerPayload> fetchByOrganizationId(
            @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity,

            @ApiParam(required = true, defaultValue = "2017-10-30T23:00")
            @RequestParam(name = "start_date", required = true)
                    LocalDateTime from,

            @ApiParam(required = true, defaultValue = "2017-10-30T23:00")
            @RequestParam(name = "end_date", required = true)
                    LocalDateTime to,
            @Valid PageableRequest pageableRequest, Pageable pageable
    ) {
        return customerService.findCustomersByOrganizationId(organizationId, from, to, userEntity, pageable);
    }

    @GetMapping(path = "/organizations/{organization-id}/services/{service-id}/customers")
    public Page<CustomerPayload> fetchByOrganizationIdAndServiceId(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity,

            @ApiParam(required = true, defaultValue = "2017-10-30T23:00")
            @RequestParam(value = "from", required = true)
                    LocalDateTime from,

            @ApiParam(required = true, defaultValue = "2017-10-30T23:00")
            @RequestParam(value = "to", required = true)
                    LocalDateTime to,
            @Valid PageableRequest pageableRequest, Pageable pageable
    ) {
        return customerService
                .findCustomersByOrganizationIdAndServiceId(organizationId, serviceId, from, to, userEntity,
                        pageable);
    }

    @GetMapping(path = "/organizations/{organization-id}/groups/{group-id}/customers")
    public Page<CustomerPayload> fetchByOrganizationIdAndGroupId(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("group-id") UUID groupId,
            @ApiIgnore UserEntity userEntity,

            @ApiParam(required = true, defaultValue = "2017-10-30T23:00")
            @RequestParam(value = "", required = true)
                    LocalDateTime from,

            @ApiParam(required = true, defaultValue = "2017-10-30T23:00")
            @RequestParam(value = "to", required = true)
                    LocalDateTime to,
            @Valid PageableRequest pageableRequest, Pageable pageable
    ) {

        return customerService.findCustomersByOrganizationIdAndGroupId(organizationId, groupId, from, to, userEntity,
                pageable);
    }


}
