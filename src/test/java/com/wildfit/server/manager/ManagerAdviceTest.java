package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.domain.ErrorData;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
class ManagerAdviceTest {

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    ManagerAdvice managerAdvice;

    @Test
    void userServiceExceptionHandler() {
        final var exception = new UserServiceException(UserServiceError.USER_NOT_FOUND);
        final var response = managerAdvice.userServiceExceptionHandler(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorData expected = ErrorData.builder()
                .withMessage(UserServiceError.USER_NOT_FOUND.getMessage())
                .withErrorCode(UserServiceError.USER_NOT_FOUND.name())
                .build();
        assertEquals(expected, response.getBody());
    }

    @Test
    void nutritionixExceptionHandler() {
        final var e = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        final var exception = new NutritionixException(e.getStatusCode(), e);
        final var response = managerAdvice.nutritionixExceptionHandler(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());

        final var errorData = response.getBody();

        assertNull(errorData.getErrorCode());
        assertTrue(errorData.getMessage().contains("BAD_REQUEST"));
        assertEquals(400, errorData.getNutritionixStatusCode());
    }
}