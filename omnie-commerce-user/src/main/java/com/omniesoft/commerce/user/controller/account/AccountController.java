package com.omniesoft.commerce.user.controller.account;

import com.omniesoft.commerce.common.payload.account.AccountDto;
import com.omniesoft.commerce.common.util.Patterns;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.account.payload.UserRegisterPayload;
import com.omniesoft.commerce.user.service.account.AccountService;
import com.omniesoft.commerce.user.service.account.EmailChangingService;
import com.omniesoft.commerce.user.service.account.EmailConfirmationService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Vitalii Martynovskyi
 * @since 16.09.17
 */

@RestController
@RequestMapping(path = "/account")
@Validated
@Api(value = "Account", tags = "Account Controller", description = "@limpid")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final EmailConfirmationService emailConfirmationService;
    private final EmailChangingService emailChangingService;


    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto register(@Valid @RequestBody UserRegisterPayload user, Device device) {
        return accountService.register(user);
    }

    @GetMapping(path = "/confirmation/reply")
    public void sendRegistrationConfirmation(@RequestParam String email) {
        emailConfirmationService.replyEmailTokenSending(email);
    }

    @GetMapping(path = "/confirmation/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmRegister(@PathVariable("token") @Size(min = 19) String token) {
        emailConfirmationService.verifyEmailToken(token);
    }

    @PutMapping(path = "/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changEmail(@Pattern(regexp = Patterns.EMAIL) @RequestParam("value") String email, @ApiIgnore UserEntity userEntity) {
        emailChangingService.changeEmail(userEntity, email);
    }

    @GetMapping(path = "/email/change/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changEmail(@PathVariable("token") @Size(min = 19) String token) {
        emailChangingService.changeEmail(token);
    }






}
