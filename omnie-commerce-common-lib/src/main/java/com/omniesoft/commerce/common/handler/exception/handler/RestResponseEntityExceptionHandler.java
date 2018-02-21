package com.omniesoft.commerce.common.handler.exception.handler;

import com.omniesoft.commerce.common.handler.exception.custom.MethodCustomParamsValidationException;
import com.omniesoft.commerce.common.handler.exception.custom.WrappedRuntimeException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.ErrorCode;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.handler.exception.message.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {OAuth2Exception.class, AccessDeniedException.class, ClientAuthenticationException
            .class})
    protected ResponseEntity<Object> handleOAuthException(Exception ex, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);

        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage("OAuth error");
        apiError.setDebugMessage(ex.getLocalizedMessage());
        apiError.setDetailedCode(SecurityModuleErrorCodes.CLIENT_CREDENTIALS);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = {WrappedRuntimeException.class})
    protected ResponseEntity<Object> handleWrappedRuntimeException(WrappedRuntimeException ex, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);

        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage(ex.getClass().getSimpleName() + " : " + ex.preparedMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        apiError.setDetailedCode(ex.getExceptionCode());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle RuntimeException. Triggered when throw .
     *
     * @param ex      RuntimeException ({IllegalArgumentException.class, IllegalStateException.class})
     * @param request WebRequest
     * @return the ApiError object
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    /**
     * Handle MissingServletRequestParameterException. Exception thrown when a request handler does not support a specific request method..
     *
     * @param ex Generic exception
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, "Internal exception", status);
    }


    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        String format = String.format("Http method type %s is not supported for this resuest", request.getContextPath());
        return getObjectResponseEntity(ex, status, format, InternalErrorCodes.INTERNAL);
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex)
                .withDetailedCode(InternalErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return getObjectResponseEntity(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder
                .length() - 2), InternalErrorCodes.HTTP_MEDIA_TYPE_NOT_SUPPORTED);
    }


    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        apiError = apiError.withDetailedCode(InternalErrorCodes.METHOD_ARGUMENT_NOT_VALID);
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ValidationException. Thrown when @Valid fails.
     *
     * @param ex the ValidationException
     * @return the ApiError object
     */
    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ValidationException ex) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError = apiError.withDetailedCode(InternalErrorCodes.CONSTRAINT_VALIDATION);
        return buildResponseEntity(apiError);
    }


    /**
     * Handles org.springframework.dao.EmptyResultDataAccessException. Thrown when @Valid fails.
     *
     * @param ex the ValidationException
     * @return the ApiError object
     */
    @ExceptionHandler({org.springframework.dao.EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            org.springframework.dao.EmptyResultDataAccessException ex) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Server occurred error");
        apiError.setDebugMessage("Result was expected to have at least one row (or element) but zero rows (or elements) were actually returned");
        apiError = apiError.withDetailedCode(InternalErrorCodes.EMPTY_RESULT_DATA_ACCESS);
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler({MethodCustomParamsValidationException.class})
    protected ResponseEntity<Object> handleMethodParamConstraintViolation(
            MethodCustomParamsValidationException ex) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getFieldErrorList());
        apiError = apiError.withDetailedCode(InternalErrorCodes.METHOD_CUSTOM_PARAMS_VALIDATION);
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        apiError = apiError.withDetailedCode(InternalErrorCodes.CONSTRAINT_VALIDATION);
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        apiError = apiError.withDetailedCode(InternalErrorCodes.ENTITY_NOT_FOUND);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return getObjectResponseEntity(ex, status, error);
    }


    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String error = "Error writing JSON output";
        log.error(this.getClass().getSimpleName(), ex);
        return buildResponseEntity(new ApiError(status, error, ex).withDetailedCode(
                InternalErrorCodes.INTERNAL));
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {

        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex).withDetailedCode(
                InternalErrorCodes.DATA_INTEGRITY_VIOLATION));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(
                String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(),
                        ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        apiError = apiError.withDetailedCode(InternalErrorCodes.METHOD_ARGUMENT_TYPE_MISMATCH);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMediaTypeNotAcceptableException. When implementing an API endpoint with Spring, we generally need to specify
     * the consumed/produced media types (via the consumes and produces parameters).
     * This narrows down the possible formats that the API will return back to the client for that specific operation.
     *
     * @param ex      HttpMediaTypeNotAcceptableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, status, "Media type is now supported", InternalErrorCodes.HTTP_MEDIA_TYPE_NOT_SUPPORTED);
    }

    /**
     * Handle MissingPathVariableException. ServletRequestBindingException subclass that indicates that a path variable expected in the method parameters of an @RequestMapping method is not
     * present among the URI variables extracted from the URL. Typically that means the URI template does not match the path variable name declared on the method parameter..
     *
     * @param ex      MissingPathVariableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(
                ex,
                status,
                "Path variable expected in the method parameters of an " + request.getContextPath() + " method is not present among the URI variables extracted from the URL", InternalErrorCodes.MISSING_REQUEST_PATH_VARIABLE);
    }

    /**
     * Handle ServletRequestBindingException. Exception thrown on a type mismatch when trying to set a bean property..
     *
     * @param ex      ServletRequestBindingException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, status, "Required parameters are not present", InternalErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER);
    }

    /**
     * Handle ConversionNotSupportedException. Exception thrown when no suitable editor or converter can be found for a bean property..
     *
     * @param ex      ConversionNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, "Internal error", status);
    }


    /**
     * Handle TypeMismatchException. Exception thrown on a type mismatch when trying to set a bean property..
     *
     * @param ex      TypeMismatchException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, "Internal error", status);
    }

    /**
     * Handle MissingServletRequestPartException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, status, "Web application is not configured correctly for processing  multipart requests", InternalErrorCodes.INCORRECT_MULTIPART_ID);
    }

    /**
     * Handle BIND_EXCEPTION. Signals that an error occurred while attempting to bind a socket to a local address and port.
     *
     * @param ex      BindException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        ApiError apiError = new ApiError(status);
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        apiError.addValidationErrors(ex.getFieldErrors());
        apiError = apiError.withDetailedCode(InternalErrorCodes.INTERNAL);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle NoHandlerFoundException. By default when the DispatcherServlet can't find a handler for a request it sends a 404 response..
     *
     * @param ex      NoHandlerFoundException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, status, "Resource not found", InternalErrorCodes.RESOURCE_NOT_FOUND);
    }


    /**
     * Handle AsyncRequestTimeoutException. Triggered when exception to be thrown when an async request times out..
     *
     * @param ex      AsyncRequestTimeoutException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        HttpHeaders headers, HttpStatus status,
                                                                        WebRequest webRequest) {

        log.error(this.getClass().getSimpleName(), ex);
        return getObjectResponseEntity(ex, status, "Async request times out", InternalErrorCodes.ASYNC_REQUEST_TIMEOUT);
    }




    /*-----------------------------------------------------------------------------------------------*/

    /**
     * @return the ApiError object
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ResponseEntity<Object> getObjectResponseEntity(HttpMessageNotReadableException ex, HttpStatus status, String error) {
        return buildResponseEntity(new ApiError(status, error, ex).withDetailedCode(
                InternalErrorCodes.INTERNAL));
    }

    protected ResponseEntity<Object> getObjectResponseEntity(Exception ex, HttpStatus status, String message, ErrorCode code) {
        return buildResponseEntity(
                new ApiError(
                        status,
                        message,
                        ex).withDetailedCode(code));
    }

    private ResponseEntity<Object> getObjectResponseEntity(Exception ex, String message, HttpStatus status) {

        ApiError apiError = new ApiError(status);
        apiError.setMessage("Server error");
        apiError.setDebugMessage(message);
        apiError = apiError.withDetailedCode(InternalErrorCodes.INTERNAL);
        return buildResponseEntity(apiError);
    }
}
