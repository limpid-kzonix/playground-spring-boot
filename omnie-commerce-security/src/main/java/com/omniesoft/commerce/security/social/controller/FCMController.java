package com.omniesoft.commerce.security.social.controller;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.security.social.service.IFCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/social/fcm")
@RequiredArgsConstructor
public class FCMController {

    // TODO: 14.06.18: should provide cleaning mechanism for fcm tokens
    private final IFCMService fcmService;

    @PutMapping(path = "/link/{fcm-token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkFCMToUser(
            @PathVariable("fcm-token") String fcmToken,
            @ApiIgnore UserEntity user) {
        fcmService.link(user, fcmToken);
    }

}
