package com.omniesoft.commerce.user.controller.account.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginPayload {
    @NotBlank
    private String login;
    @NotBlank(message = "Password must not be blank")
    @Min(value = 6)
    private String password;
}
