package com.omniesoft.commerce.statistic.controllers;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.AdminLogPayload;
import com.omniesoft.commerce.statistic.models.enums.AdminActionType;
import com.omniesoft.commerce.statistic.models.services.AdminActivityLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/owner/logs/")
public class OwnerLoggingInController {

    private AdminActivityLogService adminActivityLogService;

    @PostMapping(path = "/organizations")
    public void insertOrganizationLog(
            AdminLogPayload payload
    ) {
        adminActivityLogService.insert(payload, AdminActionType.ORGANIZATION);
    }

    @PostMapping(path = "/organizations/services")
    public void insertServiceLog(AdminLogPayload payload) {
        adminActivityLogService.insert(payload, AdminActionType.SERIVCE);
    }

    @PostMapping(path = "/organizations/news")
    public void insertNewsLog(AdminLogPayload payload) {
        adminActivityLogService.insert(payload, AdminActionType.NEWS);
    }

    @PostMapping(path = "/organizations/discounts")
    public void insertDiscountLog(AdminLogPayload payload) {
        adminActivityLogService.insert(payload, AdminActionType.DISCOUNT);
    }

    @PostMapping(path = "/organizations/clients")
    public void insertClientGroupLog(AdminLogPayload payload) {
        adminActivityLogService.insert(payload, AdminActionType.CLIENT_GROUP);
    }

    @PostMapping(path = "/organizations/orders")
    public void insertOrdersLog(AdminLogPayload payload) {
        adminActivityLogService.insert(payload, AdminActionType.ORDER);
    }

}
