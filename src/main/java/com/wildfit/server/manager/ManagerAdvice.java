package com.wildfit.server.manager;

import com.wildfit.server.domain.ErrorData;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    protected ResponseEntity<ErrorData> userServiceExceptionHandler(UserServiceException ex, WebRequest request) {
        log.error(request.getDescription(false));
        log.error("UserServiceException", ex);

        final var userServiceException = ex.getError();
        final var error = ErrorData.builder()
                                   .withMessage(userServiceException.getMessage())
                                   .withErrorCode(userServiceException.name())
                                   .build();

        return new ResponseEntity<>(error, userServiceException.getHttpStatus());
    }

    @ExceptionHandler(NutritionixException.class)
    protected ResponseEntity<ErrorData> nutritionixExceptionHandler(NutritionixException ex, WebRequest request) {
        log.error(request.getDescription(false));
        log.error("NutritionixException", ex);

        final var error = ErrorData.builder()
                                   .withMessage(ex.getMessage())
                                   .withNutritionixStatusCode(ex.getStatusCode().value())
                                   .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
