package com.omniesoft.commerce.common.handler.exception.custom;

import com.omniesoft.commerce.common.handler.exception.custom.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

public abstract class WrappedRuntimeException extends RuntimeException {

    @Setter
    @Getter
    protected Integer exceptionCode = 0;

    @Setter
    @Getter
    private String debugMessage = "";

    public WrappedRuntimeException() {
    }

    public WrappedRuntimeException(ErrorCode exceptionCode) {
        this.exceptionCode = exceptionCode.getCode();
    }

    public WrappedRuntimeException(String message) {
        super(message);
    }

    public WrappedRuntimeException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;

    }

    public WrappedRuntimeException(String message, ErrorCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode.getCode();
    }

    public WrappedRuntimeException(String message, String debugMessage, ErrorCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode.getCode();
        this.debugMessage = debugMessage;
    }

    public WrappedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrappedRuntimeException(Throwable cause) {
        super(cause);
    }

    public WrappedRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WrappedRuntimeException withCode(ErrorCode code) {
        this.exceptionCode = code.getCode();
        return this;
    }

    public String preparedMessage() {
        return String.format("Exception was throw with code %d and message: %s", exceptionCode,
                changeNull(this.getMessage()));
    }

    private String changeNull(String source) {
        if (source == null) return "No exception message";
        return source;
    }

}
