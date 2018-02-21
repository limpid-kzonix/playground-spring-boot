package com.omniesoft.commerce.statistic.controllers;

import com.omniesoft.commerce.common.ws.statistic.impl.enums.FavoriteType;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.NewsLogPayload;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.ServiceLogPayload;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.UserLogPayload;
import com.omniesoft.commerce.statistic.models.enums.UserActionType;
import com.omniesoft.commerce.statistic.models.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "users")
@AllArgsConstructor
public class UserLoggingInController {

    private UserActivityLogService userActivityLogService;

    private OrganizationViewsLogService organizationViewsLogService;

    private OrganizationFavoritesLogService organizationFavoritesLogService;

    private OrganizationShowsLogService organizationShowsLogService;

    private ServiceFavoritesLogService serviceFavoritesLogService;

    private ServiceViewsLogService serviceViewsLogService;

    private ServiceShowsLogService serviceShowsLogService;

    private OrganizationNewsShowsLogService newsShowsLogService;

    private OrganizationNewsViewsLogService newsViewsLogService;

    @PostMapping(path = "/profile")
    public void insertProfileChanges(
            @RequestBody UserLogPayload payload
    ) {

        userActivityLogService.insert(payload, UserActionType.PROFILE);
    }

    @PostMapping(path = "/password")
    public void insertPasswordChanges(
            @RequestBody UserLogPayload payload
    ) {

        userActivityLogService.insert(payload, UserActionType.PASSWORD);
    }

    @PostMapping(path = "/settings")
    public void insertSettingsChanges(
            @RequestBody UserLogPayload payload
    ) {

        userActivityLogService.insert(payload, UserActionType.SETTING);
    }

    @PostMapping(path = "/cards/omnie")
    public void insertUsingOmnieCard(
            @RequestBody UserLogPayload payload
    ) {
        userActivityLogService.insert(payload, UserActionType.OMNIE_CARD);
    }

    @PostMapping(path = "/cards/holder")
    public void insertUsingCardHolder(
            @RequestBody UserLogPayload payload
    ) {

        userActivityLogService.insert(payload, UserActionType.CARD_HOLDER);
    }

    @PostMapping(path = "/order/history")
    public void readOrderHistory(
            @RequestBody UserLogPayload payload
    ) {
        userActivityLogService.insert(payload, UserActionType.ORDER);
    }

    @PostMapping(path = "/organizations/search")
    public void insertToOrganizationSearchingLog(
            @RequestBody UserLogPayload payload,
            @RequestParam(name = "pattern") String searchingPattern
    ) {

        userActivityLogService.insertToSearchingLog(payload, searchingPattern);
    }

    @PostMapping(path = "/organizations/favorites/")
    public void insertToOrganizationFavoritesLog(
            @RequestBody OrgLogPayload payload,
            @RequestParam(name = "type") FavoriteType type
    ) {

        organizationFavoritesLogService.insert(payload, type);
    }

    @PostMapping(path = "/organizations/shows")
    public void insertToOrganizationsShowsLog(
            @RequestBody OrgLogPayload payload
    ) {

        organizationShowsLogService.insert(payload);
    }

    @PostMapping(path = "/organizations/views")
    public void insertToOrganizationViewsLog(
            @RequestBody OrgLogPayload payload
    ) {

        organizationViewsLogService.insert(payload);
    }

    @PostMapping(path = "/services/shows")
    public void insertToServiceShowsLog(
            @RequestBody ServiceLogPayload serviceLogPayload
    ) {

        serviceShowsLogService.insert(serviceLogPayload);
    }

    @PostMapping(path = "/services/views")
    public void insertToServiceViewsLog(
            @RequestBody ServiceLogPayload serviceLogPayload
    ) {

        serviceViewsLogService.insert(serviceLogPayload);
    }

    @PostMapping(path = "/services/favorites")
    public void insertToServiceFavoritesLog(
            @RequestBody ServiceLogPayload serviceLogPayload,
            @RequestParam(name = "type") FavoriteType type
    ) {
        serviceFavoritesLogService.insert(serviceLogPayload, type);
    }

    @PostMapping(path = "/news/shows")
    public void insertToNewsShowsLog(
            @RequestBody NewsLogPayload newsLogPayload
    ) {
        newsShowsLogService.insert(newsLogPayload);
    }

    @PostMapping(path = "/news/views")
    public void insertToNewsViewsLog(
            @RequestBody NewsLogPayload newsLogPayload
    ) {
        newsViewsLogService.insert(newsLogPayload);
    }


}
