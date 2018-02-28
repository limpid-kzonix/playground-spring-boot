package com.omniesoft.commerce.gateway.handler;

import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.message.ApiError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ZuulErrorController extends AbstractErrorController {

    @Value("${error.path:/error}")
    private String errorPath;

    public ZuulErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = "${error.path:/error}", produces = "application/json;charset=UTF-8")
    public ResponseEntity error(HttpServletRequest request) {
        ApiError apiError = getApiError(request);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    private ApiError getApiError(HttpServletRequest request) {
        final Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        final String message = throwable != null ? throwable.getMessage() : "Unexpected error occurred";
        return new ApiError(getStatus(request), message, throwable).withDetailedCode(InternalErrorCodes.LOAD_BALANCER_PROXY_ERROR);
    }
}
