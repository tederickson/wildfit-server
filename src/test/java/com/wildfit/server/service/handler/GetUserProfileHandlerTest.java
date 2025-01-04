package com.wildfit.server.service.handler;

import com.wildfit.server.domain.GenderType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GetUserProfileHandlerTest extends CommonHandlerTest {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> GetUserProfileHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.getUserProfile(null));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.getUserProfile("-14L"));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(PASSWORD)
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();
        final var dbUser = userRepository.save(user);
        final var userProfile = UserProfile.builder().withUser(dbUser)
                .withName(NAME)
                .withAge(39)
                .withGender('M')
                .withWeight(185.7f)
                .withHeight_feet(5)
                .withHeight_inches(2)
                .build();

        final var saved = userProfileRepository.save(userProfile);
        assertNotNull(saved);

        final var digest = userService.getUserProfile(saved.getUser().getUuid());

        assertEquals(EMAIL, digest.getUser().email());
        assertEquals(39, digest.getAge());
        assertEquals(GenderType.MALE, digest.getGender());
        assertEquals(185.7f, digest.getWeight());
        assertEquals(5, digest.getHeightFeet());
        assertEquals(2, digest.getHeightInches());
    }
}