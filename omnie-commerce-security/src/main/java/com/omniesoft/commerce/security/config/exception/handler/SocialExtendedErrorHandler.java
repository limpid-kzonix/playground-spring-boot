package com.omniesoft.commerce.security.config.exception.handler;

import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.handler.exception.handler.RestResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.SocialException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class SocialExtendedErrorHandler extends RestResponseEntityExceptionHandler {

    /**
     * Handle RuntimeException. Triggered when throw .
     *
     * @param ex      RuntimeException ({IllegalArgumentException.class, IllegalStateException.class})
     * @param request WebRequest
     * @return the ApiError object
     */
    @ExceptionHandler(value = {SocialException.class})
    protected ResponseEntity<Object> handleConflict(SocialException ex, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        String bodyOfResponse = "Uncategorized request to social service";
        return getObjectResponseEntity(ex, HttpStatus.EXPECTATION_FAILED, bodyOfResponse, SecurityModuleErrorCodes.SOCIAL_CONNECT_ERROR);
    }

}
