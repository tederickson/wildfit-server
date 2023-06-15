package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import com.wildfit.server.domain.GenderType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetUserProfileHandlerTest extends AbstractHandlerTest {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> GetUserProfileHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> GetUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserId(null)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> GetUserProfileHandler.builder()
                        .withUserRepository(userRepository)
                        .withUserProfileRepository(userProfileRepository)
                        .withUserId(-14L)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();
        final var userProfile = UserProfile.builder().withUser(user)
                .withName(NAME)
                .withAge(39)
                .withGender('M')
                .withWeight(185.7f)
                .withHeight_feet(5)
                .withHeight_inches(2)
                .build();

        final var saved = userProfileRepository.save(userProfile);
        assertNotNull(saved);

        final var digest = GetUserProfileHandler.builder()
                .withUserRepository(userRepository)
                .withUserProfileRepository(userProfileRepository)
                .withUserId(saved.getUser().getId())
                .build().execute();

        assertEquals(EMAIL, digest.getUser().getEmail());
        assertEquals(39, digest.getAge());
        assertEquals(GenderType.MALE, digest.getGender());
        assertEquals(185.7f, digest.getWeight());
        assertEquals(5, digest.getHeightFeet());
        assertEquals(2, digest.getHeightInches());
    }
}