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
import com.wildfit.server.model.VerificationToken;
import com.wildfit.server.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfirmUserHandlerTest extends AbstractHandlerTest {

    private static final String CONFIRMATION_CODE = "Apples";

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> ConfirmUserHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void invalidConfirmationCode(String confirmationCode) {
        final var exception = assertThrows(UserServiceException.class,
                () -> ConfirmUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withConfirmationCode(confirmationCode)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_CONFIRMATION_CODE, exception.getError());
    }

    @Test
    void confirmationCodeDoesNotMatch() {
        createUser();

        final var exception = assertThrows(UserServiceException.class,
                () -> ConfirmUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withConfirmationCode("BugsBunny")
                        .build().execute());
        assertEquals(UserServiceError.INVALID_CONFIRMATION_CODE, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final User saved = createUser();

        ConfirmUserHandler.builder()
                .withUserRepository(userRepository)
                .withVerificationTokenRepository(verificationTokenRepository)
                .withConfirmationCode(CONFIRMATION_CODE)
                .build().execute();

        final var updatedUser = userRepository.findById(saved.getId()).orElseThrow();
        assertEquals(UserStatus.FREE, updatedUser.getUserStatus());
        assertTrue(updatedUser.isEnabled());
    }

    private User createUser() {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword("encoded password")
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var verificationToken = new VerificationToken(CONFIRMATION_CODE, user);

        verificationTokenRepository.save(verificationToken);

        return saved;
    }
}