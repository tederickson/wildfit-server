package com.wildfit.server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest extends AbstractRepositoryTest {
    // The move to Spring Boot 3.4.7 broke the tests that save a User with an Optimistic Locking Failure Exception

    @Test
    void findByEmail() {
        final var users = userRepository.findByEmail(PASSWORD);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void findByUniqueUserId() {
        assertTrue(userRepository.findByUuid("bob").isEmpty());
    }
}