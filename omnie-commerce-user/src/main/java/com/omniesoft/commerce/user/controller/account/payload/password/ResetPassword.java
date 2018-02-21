package com.omniesoft.commerce.user.controller.account.payload.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @NotBlank
    @Email
    private String userEmail;
    @Size(min = 15, max = 15)
    @NotBlank
    private String token;
    @Size(min = 6)
    private String newPassword;
}
