package com.omniesoft.commerce.user.controller.account.payload.validate;

import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.controller.account.payload.UserRegisterPayload;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueAccountValidator implements ConstraintValidator<UniqueAccount, UserRegisterPayload> {

    private UserRepository userRepository;

    public void initialize(UniqueAccount constraint) {
    }

    public boolean isValid(UserRegisterPayload obj, ConstraintValidatorContext context) {

        Boolean isNotExistByLogin = userRepository.findByLogin(obj.getLogin().toLowerCase()) == null;
        Boolean isNotExistByEmail = userRepository.findByEmail(obj.getEmail()) == null;
        return isNotExistByEmail && isNotExistByLogin;
    }
}
