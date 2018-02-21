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

        Boolean isNotExistByLogin = userRepository.findByLogin(obj.getLogin()) == null;
        Boolean isNotExistByEmail = userRepository.findByEmail(obj.getEmail()) == null;
        if (isNotExistByEmail && isNotExistByLogin) {
            return true;
        } else {
            return preformFieldErrorsResult(context, isNotExistByLogin, isNotExistByEmail);
        }
    }

    private boolean preformFieldErrorsResult(ConstraintValidatorContext context, Boolean isNotExistByLogin, Boolean isNotExistByEmail) {
        context.disableDefaultConstraintViolation();
        preformFieldErrors(context, isNotExistByLogin, isNotExistByEmail);
        return false;
    }

    private void preformFieldErrors(ConstraintValidatorContext context, Boolean isNotExistByLogin, Boolean isNotExistByEmail) {
        if (!isNotExistByLogin) {
            addFieldError(context, "Some fields are incorrect", "undefined");
        } else if (!isNotExistByEmail)
            addFieldError(context, "Some fields are incorrect", "undefined");
    }

    private void addFieldError(ConstraintValidatorContext context, String cause, String rejetedField) {
        context.buildConstraintViolationWithTemplate(cause)
                .addPropertyNode(rejetedField).addConstraintViolation();
    }
}
