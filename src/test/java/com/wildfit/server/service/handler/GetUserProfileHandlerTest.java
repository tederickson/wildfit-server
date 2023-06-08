package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetUserProfileHandlerTest {

    private static final String PASSWORD = "Super2023!";
    private static final String USER_NAME = "Bob";

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
                () -> GetUserProfileHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingUserName(String userName) {
        final var userDigest = UserDigest.builder()
                .withUserName(userName)
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> GetUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_USER_NAME, exception.getError());
    }

    @Test
    void emptyUserName() {
        final var userDigest = UserDigest.builder()
                .withUserName("    ")
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> GetUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_USER_NAME, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var userDigest = UserDigest.builder()
                .withUserName(USER_NAME)
                .build();

        final var user = User.builder()
                .withUserName(USER_NAME)
                .withPassword(PASSWORD)
                .withEmail("bob@test.com").build();
        final var userProfile = UserProfile.builder().withUser(user)
                .withAge(39)
                .withGender('M')
                .withWeight(185.7f)
                .withHeight(65.23f)
                .build();

        final var saved = userProfileRepository.save(userProfile);
        assertNotNull(saved);
        System.out.println("saved = " + saved);

        final var digest = GetUserProfileHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserDigest(userDigest)
                .build().execute();

        assertEquals(USER_NAME, digest.getUser().getUserName());
        assertNull(digest.getUser().getPassword());
        assertEquals("bob@test.com", digest.getUser().getEmail());
        assertEquals(39, digest.getAge());
        assertEquals('M', digest.getGender());
        assertEquals(185.7f, digest.getWeight());
        assertEquals(65.23f, digest.getHeight());
    }
}