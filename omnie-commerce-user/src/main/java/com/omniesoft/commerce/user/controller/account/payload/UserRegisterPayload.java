package com.omniesoft.commerce.user.controller.account.payload;

import com.omniesoft.commerce.common.util.Patterns;
import com.omniesoft.commerce.user.controller.account.payload.validate.UniqueAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueAccount
public class UserRegisterPayload {

    @ApiModelProperty(required = true)
    @NotBlank(message = "Username must not be blank")
    @Pattern(regexp = Patterns.LOGIN, message = "Not valid with regex " + Patterns.LOGIN)
    private String login;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6)
    @ApiModelProperty(required = true, notes = "Length >= 6")
    private String password;

    @NotBlank(message = "Email must not be blank")
    @Email(regexp = Patterns.EMAIL)
    @ApiModelProperty(required = true)
    private String email;
}
