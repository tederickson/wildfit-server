package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeleteUserHandlerTest {
    private static final String PASSWORD = "Super2023!";
    private static final String EMAIL = "bob@bob.com";

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }

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
                        .withUserId(-14L)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();

        final var saved = userRepository.save(user);
        assertNotNull(saved);

        DeleteUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserId(saved.getId())
                .build().execute();

        final var users = userRepository.findByEmail(EMAIL);
        assertTrue(users.isEmpty());
    }
}