package com.omniesoft.commerce.user.controller.account.payload.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordCode {
    @Email
    @NotBlank
    private String email;

    @Min(1000)
    @Max(9999)
    private Integer code;
}
