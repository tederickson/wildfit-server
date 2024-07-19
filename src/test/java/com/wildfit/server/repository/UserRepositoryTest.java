package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest extends AbstractRepositoryTest {

    @Test
    void findByEmail() {
        final var users = userRepository.findByEmail(PASSWORD);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void findByEmail_withUser() {
        final var saved = userRepository.save(USER);
        assertNotNull(saved);

        final var users = userRepository.findByEmail(EMAIL);

        assertEquals(1, users.size());
        final var retrieved = users.getFirst();

        assertEquals(EMAIL, retrieved.getEmail());
        assertEquals(UserStatus.FREE, retrieved.getUserStatus());
    }

    @Test
    void findByUniqueUserId() {
        assertTrue(userRepository.findByUuid("bob").isEmpty());
    }

    @Test
    void findByUniqueUserId_withUser() {
        final var saved = userRepository.save(USER);
        assertNotNull(saved);

        final var user = userRepository.findByUuid(saved.getUuid())
                                       .orElse(null);


        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(UserStatus.FREE, user.getUserStatus());
    }
}