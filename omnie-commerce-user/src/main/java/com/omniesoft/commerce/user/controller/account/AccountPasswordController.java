package com.omniesoft.commerce.user.controller.account;


import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.account.payload.password.ChangePassword;
import com.omniesoft.commerce.user.controller.account.payload.password.PasswordResetDto;
import com.omniesoft.commerce.user.controller.account.payload.password.ResetPassword;
import com.omniesoft.commerce.user.controller.account.payload.password.ResetPasswordCode;
import com.omniesoft.commerce.user.service.account.AccountPasswordService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/account/password")
@Validated
@Api(value = "Account", tags = "Account Controller", description = "@limpid")
@AllArgsConstructor
public class AccountPasswordController {

    private final AccountPasswordService accountPasswordService;


    @GetMapping(path = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePasswordRequest(@Valid @RequestParam @Email String email) {
        accountPasswordService.setPasswordResetCode(email);
    }

    @PatchMapping(path = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
    public PasswordResetDto changePassword(@Valid @RequestBody ResetPasswordCode resetPasswordCode) {
        return accountPasswordService.verifyResetPasswordCode(resetPasswordCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = "/forgot/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setNewPassword(@Valid @RequestBody ResetPassword resetPassword) {
        accountPasswordService.resetPassword(resetPassword);
    }

    @PutMapping(path = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePassword changePassword, @ApiIgnore UserEntity userEntity) {
        accountPasswordService.changePassword(changePassword, userEntity);
    }
}
