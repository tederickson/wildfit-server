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
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class LoginHandlerTest extends CommonHandlerTest {

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class, () -> LoginHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullPassword(String password) {
        final var exception = assertThrows(WildfitServiceException.class, () -> userService.login(EMAIL, password));
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingEmail(String email) {
        final var exception = assertThrows(WildfitServiceException.class, () -> userService.login(email, PASSWORD));
        assertEquals(WildfitServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var exception = assertThrows(WildfitServiceException.class, () -> userService.login("    ", PASSWORD));
        assertEquals(WildfitServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(WildfitServiceException.class, () -> userService.login(EMAIL, PASSWORD));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotEnabled() {
        createUser(false);
        final var exception = assertThrows(WildfitServiceException.class, () -> userService.login(EMAIL, PASSWORD));
        assertEquals(WildfitServiceError.NOT_REGISTERED, exception.getError());
    }

    @Test
    void doesNotMatchPassword() {
        createUser(true);
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.login(EMAIL, "AndLo792134*"));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var user = createUser(true);
        final var response = userService.login(EMAIL, PASSWORD);

        assertNotNull(response);
        assertEquals(EMAIL, response.getEmail());
        assertEquals(user.getId(), response.getId());
    }

    private User createUser(boolean enabled) {
        if (PasswordValidator.isNotValid(PASSWORD)) {
            fail(WildfitServiceError.INVALID_PASSWORD.getMessage());
        }
        final var encodedPassword = PasswordEncodeDecode.encode(PASSWORD);
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(encodedPassword)
                .withEmail(EMAIL)
                .withUuid(UUID.randomUUID().toString())
                .withEnabled(enabled)
                .build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        return saved;
    }
}