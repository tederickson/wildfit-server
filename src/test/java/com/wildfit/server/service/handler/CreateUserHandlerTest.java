package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateUserHandlerTest {

    private static final String PASSWORD = "Super2023!";
    private static final String EMAIL = "bob@bob.com";

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository userProfileRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> CreateUserHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullPassword(String password) {
        final var userDigest = UserDigest.builder()
                .withEmail(EMAIL)
                .withPassword(password).build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @Test
    void invalidPassword() {
        final var userDigest = UserDigest.builder()
                .withEmail(EMAIL)
                .withPassword("apple")
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PASSWORD, exception.getError());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingEmail(String email) {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withEmail(email)
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withEmail("    ")
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withEmail(EMAIL)
                .build();
        final var saved = CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserDigest(userDigest)
                .build().execute();

        assertEquals(EMAIL, saved.getEmail());
        assertNull(saved.getPassword());
    }

    @Test
    void userAlreadyExists() {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withEmail(EMAIL)
                .build();

        final var user = User.builder()
                .withUserName(EMAIL)
                .withPassword("encodedPassword")
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());

        assertEquals(UserServiceError.EXISTING_USER, exception.getError());
    }

}