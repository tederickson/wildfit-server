package com.wildfit.server.manager;

import com.wildfit.server.domain.ErrorData;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ManagerAdviceTest {

    @InjectMocks
    ManagerAdvice managerAdvice;
    @Mock
    private WebRequest webRequest;

    @Test
    void userServiceExceptionHandler() {
        final var exception = new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND);
        final var response = managerAdvice.userServiceExceptionHandler(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorData expected = ErrorData.builder()
                .withMessage(WildfitServiceError.USER_NOT_FOUND.getMessage())
                .withErrorCode(WildfitServiceError.USER_NOT_FOUND.name())
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