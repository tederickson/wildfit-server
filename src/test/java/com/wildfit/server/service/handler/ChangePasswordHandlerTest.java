package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChangePasswordHandlerTest {
    private static final String PASSWORD = "Super2023!";
    private static final String EMAIL = "bob@bob.com";
    private static final Long USER_ID = 1222L;

    @Autowired
    UserRepository userRepository;


    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }

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
                .withStatus(UserStatus.CREATE.getCode())
                .withCreateDate(new Date())
                .withPassword("encoded password")
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        ChangePasswordHandler.builder()
                .withUserRepository(userRepository)
                .withPassword(PASSWORD)
                .withUserId(saved.getId())
                .build().execute();

        final var updatedUser = userRepository.findById(saved.getId()).orElseThrow();
        assertNotSame(saved.getPassword(), updatedUser.getPassword());
    }
}