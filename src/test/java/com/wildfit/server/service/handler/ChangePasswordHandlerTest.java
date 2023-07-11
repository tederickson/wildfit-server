package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChangePasswordHandlerTest extends CommonHandlerTest {

    private static final String USER_ID = "1222L";

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> ChangePasswordHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullPassword(String password) {
        final var exception = assertThrows(UserServiceException.class,
                () -> ChangePasswordHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserId(USER_ID)
                        .withPassword(password)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void invalidPassword() {
        final var exception = assertThrows(UserServiceException.class,
                () -> ChangePasswordHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword("apple")
                        .withUserId(USER_ID)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> ChangePasswordHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword(PASSWORD)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword("encoded password")
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        ChangePasswordHandler.builder()
                .withUserRepository(userRepository)
                .withPassword(PASSWORD)
                .withUserId(saved.getUuid())
                .build().execute();

        final var updatedUser = userRepository.findById(saved.getId()).orElseThrow();
        assertNotSame(saved.getPassword(), updatedUser.getPassword());
    }
}