package com.omniesoft.commerce.owner.controller.notification;

import com.omniesoft.commerce.common.component.notification.INotifService;
import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
public class NotifController {

    private final INotifService notifService;

    @GetMapping(path = "/notifications")
    public Page<NotifMessage<OrderNotifPl>> findNotifications(@ApiIgnore UserEntity user,
                                                              @Valid PageableRequest pageableRequest, Pageable pageable) {
        return notifService.findAdminNotif(user, pageable);
    }
}
