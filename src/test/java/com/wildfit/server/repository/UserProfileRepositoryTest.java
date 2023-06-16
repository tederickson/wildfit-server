package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserProfileRepositoryTest extends AbstractRepositoryTest {
    private static final String USER_NAME = "Bob Tester";

    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    void findByUserName() {
        final var users = userRepository.findByEmail(EMAIL);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void findByEmail_withUser() {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();
        final var userProfile = UserProfile.builder()
                .withUser(user)
                .withName(USER_NAME)
                .build();

        final var saved = userProfileRepository.save(userProfile);

        assertNotNull(saved);

        final var users = userRepository.findByEmail(EMAIL);

        assertEquals(1, users.size());
        final var retrieved = users.get(0);

        assertEquals(EMAIL, retrieved.getEmail());
        assertEquals(UserStatus.FREE, retrieved.getUserStatus());

        final var retrievedProfile = userProfileRepository.findByUser(users.get(0));

        assertEquals(EMAIL, retrievedProfile.getUser().getEmail());
        assertEquals(USER_NAME, retrievedProfile.getName());
    }
}