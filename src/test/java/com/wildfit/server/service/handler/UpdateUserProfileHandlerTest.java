package com.wildfit.server.service.handler;

import com.wildfit.server.domain.GenderType;
import com.wildfit.server.domain.UpdateUserProfileRequest;
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
class UpdateUserProfileHandlerTest extends CommonHandlerTest {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> UpdateUserProfileHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        assertThrows(NullPointerException.class,
                     () -> userService.updateUserProfile(null, UpdateUserProfileRequest.builder().build()));
    }

    @Test
    void missingUserProfileRequest() {
        assertThrows(NullPointerException.class,
                     () -> userService.updateUserProfile("123L", null));
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.updateUserProfile("-14L",
                                                                               UpdateUserProfileRequest.builder()
                                                                                       .withName(NAME).build()));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.PREMIUM.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(PASSWORD)
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();
        final var dbUser = userRepository.save(user);
        final var saved = userProfileRepository.save(UserProfile.builder()
                                                             .withUser(dbUser)
                                                             .withName(NAME).build());
        assertNotNull(saved);

        final var updateUserProfileRequest = UpdateUserProfileRequest.builder()
                .withName("Fluffy Bunny")
                .withAge(39)
                .withGender(GenderType.MALE)
                .withWeight(185.7f)
                .withHeightFeet(5)
                .withHeightInches(7)
                .build();

        final var digest = userService.updateUserProfile(saved.getUser().getUuid(), updateUserProfileRequest);

        assertEquals(EMAIL, digest.getUser().getEmail());
        assertEquals("Fluffy Bunny", digest.getName());
        assertEquals(39, digest.getAge());
        assertEquals(GenderType.MALE, digest.getGender());
        assertEquals(185.7f, digest.getWeight());
        assertEquals(5, digest.getHeightFeet());
        assertEquals(7, digest.getHeightInches());
    }
}