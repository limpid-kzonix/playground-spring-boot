package com.omniesoft.commerce.user.controller.profile;


import com.omniesoft.commerce.common.payload.account.AccountDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.profile.payload.UserProfilePayload;
import com.omniesoft.commerce.user.service.profile.UserProfileService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/profile")
@Validated
@Api(value = "Profile", tags = "Profile Controller", description = "@limpid")
@AllArgsConstructor
public class ProfileController {

    private final UserProfileService profileService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto userProfile(@ApiIgnore UserEntity userEntity) {
        return profileService.find(userEntity);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto updateProfile(@RequestBody @Valid UserProfilePayload profilePayload, @ApiIgnore UserEntity userEntity) {
        return profileService.save(profilePayload, userEntity);
    }


}
