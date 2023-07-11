package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeleteUserHandlerTest extends CommonHandlerTest {

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> DeleteUserHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserId(null)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserId("-14L")
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(PASSWORD)
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();

        final var saved = userRepository.save(user);
        assertNotNull(saved);

        DeleteUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserId(saved.getUuid())
                .build().execute();

        final var users = userRepository.findByEmail(EMAIL);
        assertTrue(users.isEmpty());
    }
}