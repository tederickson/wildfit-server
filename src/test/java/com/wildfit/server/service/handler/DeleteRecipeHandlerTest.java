package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeleteRecipeHandlerTest extends AbstractRecipeHandlerTest {
    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> DeleteRecipeHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withRecipeId(-1L)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withUserId(-14L)
                        .withRecipeId(-1L)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }
}