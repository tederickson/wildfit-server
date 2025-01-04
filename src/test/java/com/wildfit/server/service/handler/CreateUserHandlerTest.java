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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CreateUserHandlerTest extends CommonHandlerTest {

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> CreateUserHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullPassword(String password) {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(EMAIL, password, NAME));
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void invalidPassword() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(EMAIL, "apple", NAME));
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingEmail(String email) {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(email, PASSWORD, NAME));
        assertEquals(WildfitServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser("    ", PASSWORD, NAME));
        assertEquals(WildfitServiceError.MISSING_EMAIL, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingName(String name) {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(EMAIL, PASSWORD, name));
        assertEquals(WildfitServiceError.INVALID_NAME, exception.getError());
    }

    @Test
    void emptyName() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(EMAIL, PASSWORD, "    "));
        assertEquals(WildfitServiceError.INVALID_NAME, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var response = userService.createUser(EMAIL, PASSWORD, NAME);

        assertEquals(EMAIL, response.email());

        final var user = userRepository.findById(response.id()).orElseThrow();
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void userAlreadyExists() {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword("encoded password")
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.createUser(EMAIL, PASSWORD, NAME));

        assertEquals(WildfitServiceError.EXISTING_USER, exception.getError());
    }
}