package com.omniesoft.commerce.user.controller.setting;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.setting.payload.UserSettingPayload;
import com.omniesoft.commerce.user.service.setting.UserSettingsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/settings")
@Api(value = "Settings", tags = "Settings Controller", description = "@limpid")
@AllArgsConstructor
public class SettingController {

    private final UserSettingsService settingsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSettingPayload userSettings(@ApiIgnore UserEntity userEntity) {
        return settingsService.find(userEntity);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSettingPayload updateSetting(@Valid @RequestBody UserSettingPayload settingPayload, @ApiIgnore UserEntity userEntity) {
        return settingsService.update(userEntity, settingPayload);
    }
}
