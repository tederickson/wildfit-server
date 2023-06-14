package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class CreateUserHandlerTest extends AbstractHandlerTest {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    Environment environment;
    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> CreateUserHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullPassword(String password) {
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withEnvironment(environment)
                        .withJavaMailSender(javaMailSender)
                        .withEmail(EMAIL)
                        .withPassword(password)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void invalidPassword() {
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withEnvironment(environment)
                        .withJavaMailSender(javaMailSender)
                        .withPassword("apple")
                        .withEmail(EMAIL)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingEmail(String email) {
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withEnvironment(environment)
                        .withJavaMailSender(javaMailSender)
                        .withPassword(PASSWORD)
                        .withEmail(email)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withEnvironment(environment)
                        .withJavaMailSender(javaMailSender)
                        .withPassword(PASSWORD)
                        .withEmail("    ")
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var response = CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withVerificationTokenRepository(verificationTokenRepository)
                .withEnvironment(environment)
                .withJavaMailSender(javaMailSender)
                .withPassword(PASSWORD)
                .withEmail(EMAIL)
                .build().execute();

        assertEquals(EMAIL, response.getEmail());

        final var user = userRepository.findById(response.getId()).orElseThrow();
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void userAlreadyExists() {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword("encoded password")
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withVerificationTokenRepository(verificationTokenRepository)
                        .withEnvironment(environment)
                        .withJavaMailSender(javaMailSender)
                        .withPassword(PASSWORD)
                        .withEmail(EMAIL)
                        .build().execute());

        assertEquals(UserServiceError.EXISTING_USER, exception.getError());
    }

}