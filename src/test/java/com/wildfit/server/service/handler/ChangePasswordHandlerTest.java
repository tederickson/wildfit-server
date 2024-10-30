package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.changePassword(USER_ID, password));
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void invalidPassword() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.changePassword(USER_ID, "apple"));
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> ChangePasswordHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withPassword(PASSWORD)
                                                   .build().execute());
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void invalidId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.changePassword("Invalid", PASSWORD));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword("encoded password")
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        userService.changePassword(saved.getUuid(), PASSWORD);

        final var updatedUser = userRepository.findById(saved.getId()).orElseThrow();
        assertNotSame(saved.getPassword(), updatedUser.getPassword());
    }
}