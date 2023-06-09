package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import com.wildfit.server.domain.GenderType;
import com.wildfit.server.domain.UserDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
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
                () -> GetUserProfileHandler.builder().build().execute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void missingUserName(String email) {
        final var userDigest = UserDigest.builder()
                .withEmail(email)
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> GetUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void emptyEmail() {
        final var userDigest = UserDigest.builder()
                .withEmail("    ")
                .build();
        final var exception = assertThrows(UserServiceException.class,
                () -> GetUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserDigest(userDigest)
                        .build().execute());
        assertEquals(UserServiceError.MISSING_EMAIL, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var userDigest = UserDigest.builder()
                .withEmail(EMAIL)
                .build();

        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();
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

        assertEquals(EMAIL, digest.getUser().getEmail());
        assertNull(digest.getUser().getPassword());
        assertEquals(EMAIL, digest.getUser().getEmail());
        assertEquals(39, digest.getAge());
        assertEquals(GenderType.MALE, digest.getGender());
        assertEquals(185.7f, digest.getWeight());
        assertEquals(65.23f, digest.getHeight());
    }
}