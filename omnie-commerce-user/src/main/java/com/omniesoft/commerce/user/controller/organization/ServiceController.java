package com.omniesoft.commerce.user.controller.organization;

import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;
import com.omniesoft.commerce.user.controller.organization.payload.ServiceTimingPayload;
import com.omniesoft.commerce.user.service.organization.ServiceScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
@Api(value = "Service", tags = "Service Controller", description = "@limpid")
@AllArgsConstructor
public class ServiceController extends AbstractOrganizationController {

    private final ServiceScopeService serviceScopeService;
    private final ModelMapper modelMapper;

    @GetMapping(path = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ServiceRowUserExtendDto> getServices(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @RequestParam("category") UUID category,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeService.getServicesByCategoryAndFilter(searchPattern, category, pageable, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ServiceRowUserExtendDto> getOrgServices(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID orgId,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeService.getServicesByOrganizationIdAndFilter(searchPattern, orgId, pageable, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceRowUserExtendDto getService(
            @PathVariable("service-id") UUID service,
            @PathVariable("organization-id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeService.find(organization, service, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceGallerySummary> getServiceGallery(
            @PathVariable("service-id") UUID service,
            @PathVariable("organization-id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeService.getServiceGallery(service, organization, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/timesheet", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Map<DayOfWeek, List<ServicePricePayload>> getServiceTimeSheet(
            @PathVariable("service-id") UUID service,
            @PathVariable("organization-id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {

        return serviceScopeService
                .getServiceTimeSheet(service, organization, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/settings", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ServiceTimingPayload getServiceTimingSetting(
            @PathVariable("service-id") UUID service,
            @PathVariable("organization-id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {
        ServiceTimingEntity serviceTimingSetting = serviceScopeService
                .getServiceTimingSetting(organization, service, userEntity);
        return modelMapper.map(serviceTimingSetting, ServiceTimingPayload.class);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/sub-services", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public List<SubServicePayload> getSubServices(
            @PathVariable("service-id") UUID service,
            @PathVariable("organization-id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {
        return serviceScopeService.getSubServices(organization, service, userEntity);
    }


}
