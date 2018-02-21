package com.omniesoft.commerce.user.controller.organization;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationFavoriteSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceFavoriteSummary;
import com.omniesoft.commerce.user.service.favorite.FavoriteService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Validated
@AllArgsConstructor
@Api(value = "Favorite", tags = "Favorite Controller", description = "@limpid")
@RequestMapping(path = "/favorites")
public class FavoriteController {

    private FavoriteService favoriteService;

    @GetMapping(path = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrganizationFavoriteSummary> getFavoriteOrganizations(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity) {

        return favoriteService
                .getOrganizationFavorites(userEntity, pageable);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @PostMapping(path = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addOrganizationToToFavorites(
            @RequestParam("organization_id") UUID id,
            @ApiIgnore UserEntity userEntity) {

        favoriteService.addOrganizationToFavorites(id, userEntity);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping(path = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrganizationFromFavorites(
            @RequestParam("organization_id") UUID id,
            @ApiIgnore UserEntity userEntity) {

        favoriteService.deleteOrganizationFromFavorites(id, userEntity);
    }

    @GetMapping(path = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ServiceFavoriteSummary> getFavoriteServices(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity) {

        return favoriteService.getServiceFavorites(userEntity, pageable);
    }

    @PostMapping(path = "/services",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addServiceToToFavorites(
            @RequestParam("service_id") UUID id,
            @ApiIgnore UserEntity userEntity) {

        favoriteService.addServiceToFavorites(id, userEntity);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping(path = "/services", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public void deleteServiceFromFavorites(
            @RequestParam("service_id") UUID id,
            @ApiIgnore UserEntity userEntity) {

        favoriteService.deleteServiceFromFavorites(id, userEntity);
    }
}
