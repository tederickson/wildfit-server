package com.wildfit.server.manager;

import com.wildfit.server.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Provide full control of both the response body and status code.
 */
@ControllerAdvice
@Slf4j
public class ManagerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    protected ResponseEntity<Object> serviceExceptionHandler(UserServiceException ex, WebRequest request) {
        log.error("UserServiceException", ex);

        final var error = ex.getError();
        return handleExceptionInternal(ex, error.getMessage(),
                new HttpHeaders(),
                error.getHttpStatus(), request);
    }
}
