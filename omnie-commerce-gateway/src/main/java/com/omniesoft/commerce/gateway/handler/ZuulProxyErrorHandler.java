package com.omniesoft.commerce.gateway.handler;

import com.netflix.zuul.exception.ZuulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.handler.RestResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class ZuulProxyErrorHandler extends RestResponseEntityExceptionHandler {

    /**
     * Handle ZuulException.
     *
     * @param ex      ZuulException ({ZuulException.class, ZuulRuntimeException.class})
     * @param request WebRequest
     * @return the ApiError object
     */
    @ExceptionHandler(value = {ZuulException.class})
    protected ResponseEntity<Object> handleZuulError(ZuulException ex, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        String bodyOfResponse = "Zuul-proxy service unavailable: " + ex.errorCause;
        return getObjectResponseEntity(ex, HttpStatus.valueOf(ex.nStatusCode), bodyOfResponse, InternalErrorCodes.LOAD_BALANCER_PROXY_ERROR);
    }

    /**
     * Handle ZuulException.
     *
     * @param ex      ZuulException ({ZuulException.class, ZuulRuntimeException.class})
     * @param request WebRequest
     * @return the ApiError object
     */
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handle404Error(ResourceNotFoundException ex, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        String bodyOfResponse = "Not found: " + ex.getPropertyName();
        return getObjectResponseEntity(ex, HttpStatus.NOT_FOUND, bodyOfResponse, InternalErrorCodes.RESOURCE_NOT_FOUND);
    }

}
