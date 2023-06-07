package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateUserHandlerTest {

    private static final String PASSWORD = "Super2023!";
    private static final String USER_NAME = "Bob";

    @BeforeEach
    void setUp() {
    }

    @Autowired
    UserRepository userRepository;

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
                .withUserName(USER_NAME)
                .withPassword(password).build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        System.out.println("exception = " + exception);
    }

    @Test
    void invalidPassword() {
        final var userDigest = UserDigest.builder()
                .withUserName(USER_NAME)
                .withPassword("apple")
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        System.out.println("exception = " + exception);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingUserName(String userName) {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withUserName(userName)
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        System.out.println("exception = " + exception);
    }

    @Test
    void emptyUserName() {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withUserName("    ")
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        System.out.println("exception = " + exception);
    }

    @Test
    void execute() throws UserServiceException {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withUserName(USER_NAME)
                .build();
        final var saved = CreateUserHandler.builder()
                .withUserRepository(userRepository)
                .withUserDigest(userDigest)
                .build().execute();

        assertEquals(USER_NAME, saved.getUserName());
        assertNull(saved.getPassword());
        assertNull(saved.getEmail());
    }

    @Test
    void userAlreadyExists() {
        final var userDigest = UserDigest.builder()
                .withPassword(PASSWORD)
                .withUserName(USER_NAME)
                .build();

        final var user = User.builder()
                .withUserName(USER_NAME)
                .withPassword("encodedPassword")
                .withEmail("bob@test.com").build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var exception = assertThrows(UserServiceException.class,
                () -> CreateUserHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserDigest(userDigest)
                        .build().execute());

        System.out.println("exception = " + exception);
    }

}