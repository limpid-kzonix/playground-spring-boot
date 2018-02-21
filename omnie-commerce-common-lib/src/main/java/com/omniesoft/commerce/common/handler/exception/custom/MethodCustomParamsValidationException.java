package com.omniesoft.commerce.common.handler.exception.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MethodCustomParamsValidationException extends WrappedRuntimeException {
    private List<FieldError> fieldErrorList;

    public MethodCustomParamsValidationException(List<FieldError> fieldErrorList) {
        super("Method params may by wrong (Validation failed for arguments)");
        this.fieldErrorList = fieldErrorList;
    }

    public MethodCustomParamsValidationException(String message, List<FieldError> fieldErrorList) {
        super(message);
        this.fieldErrorList = fieldErrorList;
    }

    public MethodCustomParamsValidationException(String message, Throwable cause, List<FieldError> fieldErrorList) {
        super(message, cause);
        this.fieldErrorList = fieldErrorList;
    }

    public MethodCustomParamsValidationException(Throwable cause, List<FieldError> fieldErrorList) {
        super(cause);
        this.fieldErrorList = fieldErrorList;
    }

    public MethodCustomParamsValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<FieldError> fieldErrorList) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.fieldErrorList = fieldErrorList;
    }
}
