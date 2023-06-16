package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginHandlerTest extends AbstractHandlerTest {

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> LoginHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullPassword(String password) {
        final var exception = assertThrows(UserServiceException.class,
                () -> LoginHandler.builder()
                        .withUserRepository(userRepository)
                        .withEmail(EMAIL)
                        .withPassword(password)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingEmail(String email) {
        final var exception = assertThrows(UserServiceException.class,
                () -> LoginHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword(PASSWORD)
                        .withEmail(email)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var exception = assertThrows(UserServiceException.class,
                () -> LoginHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword(PASSWORD)
                        .withEmail("    ")
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> LoginHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword(PASSWORD)
                        .withEmail(EMAIL)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotEnabled() {
        createUser(false);
        final var exception = assertThrows(UserServiceException.class,
                () -> LoginHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword(PASSWORD)
                        .withEmail(EMAIL)
                        .build().execute());
        assertEquals(UserServiceError.NOT_REGISTERED, exception.getError());
    }

    @Test
    void doesNotMatchPassword() {
        createUser(true);
        final var exception = assertThrows(UserServiceException.class,
                () -> LoginHandler.builder()
                        .withUserRepository(userRepository)
                        .withPassword("AndLo792134*")
                        .withEmail(EMAIL)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        createUser(true);
        final var response = LoginHandler.builder()
                .withUserRepository(userRepository)
                .withPassword(PASSWORD)
                .withEmail(EMAIL)
                .build().execute();

        assertNotNull(response);
        assertEquals(EMAIL, response.getEmail());
    }

    private User createUser(boolean enabled) {
        if (!PasswordValidator.isValid(PASSWORD)) {
            fail(UserServiceError.INVALID_PASSWORD.getMessage());
        }
        final var encodedPassword = PasswordEncodeDecode.encode(PASSWORD);
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword(encodedPassword)
                .withEmail(EMAIL)
                .withEnabled(enabled)
                .build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        return saved;
    }
}