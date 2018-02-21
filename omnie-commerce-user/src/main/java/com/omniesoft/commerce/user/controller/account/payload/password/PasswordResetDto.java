package com.omniesoft.commerce.user.controller.account.payload.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDto {
    private UUID id;
    private Integer code;
    private String resetToken;
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
}
