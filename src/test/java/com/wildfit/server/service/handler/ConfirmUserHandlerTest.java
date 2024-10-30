package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.model.VerificationToken;
import com.wildfit.server.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ConfirmUserHandlerTest extends CommonHandlerTest {

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
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> ConfirmUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withConfirmationCode(confirmationCode)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_CONFIRMATION_CODE, exception.getError());
    }

    @Test
    void confirmationCodeDoesNotMatch() {
        createUser();

        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> ConfirmUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withConfirmationCode("BugsBunny")
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_CONFIRMATION_CODE, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
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
                .withCreateDate(java.time.LocalDate.now())
                .withPassword("encoded password")
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var verificationToken = new VerificationToken(CONFIRMATION_CODE, user);

        verificationTokenRepository.save(verificationToken);

        return saved;
    }
}