package com.omniesoft.commerce.notification.controller;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.notification.service.INotifService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class NotifController {

    private final INotifService notifService;

    @PostMapping(path = "organization/{organization-id}/service/{service-id}/notifications")
    public Page<NotifMessage<OrderNotifPl>> findNotifications(@PathVariable("organization-id") UUID organizationId,
                                                              @PathVariable("service-id") UUID serviceId,
                                                              @ApiIgnore UserEntity user,
                                                              @Valid PageableRequest pageableRequest, Pageable pageable) {

        return notifService.find(organizationId, serviceId, user, pageable);
    }
}
