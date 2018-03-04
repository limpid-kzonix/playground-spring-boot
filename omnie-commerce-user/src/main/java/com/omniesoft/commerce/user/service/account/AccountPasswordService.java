package com.omniesoft.commerce.user.service.account;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.account.payload.password.ChangePassword;
import com.omniesoft.commerce.user.controller.account.payload.password.PasswordResetDto;
import com.omniesoft.commerce.user.controller.account.payload.password.ResetPassword;
import com.omniesoft.commerce.user.controller.account.payload.password.ResetPasswordCode;

public interface AccountPasswordService {
    void setPasswordResetCode(String email);

    PasswordResetDto verifyResetPasswordCode(ResetPasswordCode resetPasswordCode);

    void resetPassword(ResetPassword resetPassword);

    void changePassword(ChangePassword changePassword, UserEntity userEntity);
}
