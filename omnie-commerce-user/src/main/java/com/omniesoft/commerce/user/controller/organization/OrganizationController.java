package com.omniesoft.commerce.user.controller.organization;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.common.payload.service.ServiceWithPricePayload;
import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import com.omniesoft.commerce.user.service.organization.OrganizationService;
import com.omniesoft.commerce.user.service.organization.ServiceScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Validated
@Api(value = "Organization", tags = "Organization Controller", description = "@limpid")
@AllArgsConstructor
public class OrganizationController extends AbstractOrganizationController {

    private final OrganizationService organizationService;
    private final ServiceScopeService serviceScopeService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrganizationRowExtendDto> getOrganizations(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @RequestParam("category") UUID category,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern,
            @ApiIgnore UserEntity userEntity) {
        return organizationService.getOrganizations(searchPattern, category, pageable, userEntity);
    }

    @GetMapping(path = "/{organization-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationRowExtendDto getOrganization(
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity) {
        return organizationService.findById(id, userEntity);
    }

    @GetMapping(path = "/{organization-id}/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrganizationGallerySummary> getOrganizationGallery(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity) {
        return organizationService.getOrganizationGallery(id, pageable, userEntity);
    }

    @GetMapping(path = "/{organization-id}/timesheet", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<DayOfWeek, List<OrganizationTimesheetDto>> getOrganizationTimeSheet(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity) {
        return organizationService.getOrganizationTimeSheet(id, pageable, userEntity);
    }

    @GetMapping(path = "/{organization-id}/phones", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrganizationPhoneSummary> getOrganizationPhones(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity) {
        return organizationService.getOrganizationPhones(id, pageable, userEntity);
    }


    @GetMapping(path = "/{organization-id}/services/pricelist")
    @ApiModelProperty(dataType = "java.util.HashMap")
    public Map<UUID, List<ServiceWithPricePayload>> getServicePriceList(
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeService.getPriceList(id, userEntity);
    }
}
