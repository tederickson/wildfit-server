package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CreateUserHandlerTest extends CommonHandlerTest {
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
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withEmail(EMAIL)
                                                   .withPassword(password)
                                                   .withName(NAME)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void invalidPassword() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withPassword("apple")
                                                   .withEmail(EMAIL)
                                                   .withName(NAME)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PASSWORD, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingEmail(String email) {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withPassword(PASSWORD)
                                                   .withEmail(email)
                                                   .withName(NAME)
                                                   .build().execute());
        assertEquals(WildfitServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withPassword(PASSWORD)
                                                   .withEmail("    ")
                                                   .withName(NAME)
                                                   .build().execute());
        assertEquals(WildfitServiceError.MISSING_EMAIL, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingName(String name) {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withPassword(PASSWORD)
                                                   .withEmail(EMAIL)
                                                   .withName(name)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_NAME, exception.getError());
    }

    @Test
    void emptyName() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withPassword(PASSWORD)
                                                   .withEmail(EMAIL)
                                                   .withName("    ")
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_NAME, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var response = CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withVerificationTokenRepository(verificationTokenRepository)
                .withEnvironment(environment)
                .withJavaMailSender(javaMailSender)
                .withPassword(PASSWORD)
                .withEmail(EMAIL)
                .withName(NAME)
                .build().execute();

        assertEquals(EMAIL, response.getEmail());

        final var user = userRepository.findById(response.getId()).orElseThrow();
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

        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> CreateUserHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withUserProfileRepository(userProfileRepository)
                                                   .withVerificationTokenRepository(verificationTokenRepository)
                                                   .withEnvironment(environment)
                                                   .withJavaMailSender(javaMailSender)
                                                   .withPassword(PASSWORD)
                                                   .withEmail(EMAIL)
                                                   .withName(NAME)
                                                   .build().execute());

        assertEquals(WildfitServiceError.EXISTING_USER, exception.getError());
    }

}