package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import com.wildfit.server.domain.GenderType;
import com.wildfit.server.domain.UpdateUserProfileRequest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateUserProfileHandlerTest {

    private static final String PASSWORD = "Super2023!";
    private static final String EMAIL = "bob@bob.com";

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository userProfileRepository;

    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> UpdateUserProfileHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        assertThrows(NullPointerException.class,
                () -> UpdateUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserId(null)
                        .withUserProfileRequest(UpdateUserProfileRequest.builder().build())
                        .build().execute());
    }

    @Test
    void missingUserProfileRequest() {
        assertThrows(NullPointerException.class,
                () -> UpdateUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserId(123L)
                        .withUserProfileRequest(null)
                        .build().execute());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserId(-14L)
                        .withUserProfileRequest(UpdateUserProfileRequest.builder().build())
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.PREMIUM.getCode())
                .withCreateDate(new Date())
                .withConfirmCode("confcode")
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();
        final var saved = userProfileRepository.save(UserProfile.builder().withUser(user).build());
        assertNotNull(saved);

        final var updateUserProfileRequest = UpdateUserProfileRequest.builder()
                .withName("Fluffy Bunny")
                .withAge(39)
                .withGender(GenderType.MALE)
                .withWeight(185.7f)
                .withHeight(65.23f)
                .build();

        final var digest = UpdateUserProfileHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserId(saved.getUser().getId())
                .withUserProfileRequest(updateUserProfileRequest)
                .build().execute();

        assertEquals(EMAIL, digest.getUser().getEmail());
        assertEquals("Fluffy Bunny", digest.getName());
        assertEquals(39, digest.getAge());
        assertEquals(GenderType.MALE, digest.getGender());
        assertEquals(185.7f, digest.getWeight());
        assertEquals(65.23f, digest.getHeight());
    }
}