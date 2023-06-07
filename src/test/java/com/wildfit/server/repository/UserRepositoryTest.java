package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
    private static final String USER_NAME = "Bob Tester";

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByUserName() {
        final var users = userRepository.findByUserName(USER_NAME);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void findByUserName_withUser() {
        final var user = User.builder()
                .withUserName(USER_NAME)
                .withPassword("encodedPassword")
                .withEmail("bob@test.com").build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var users = userRepository.findByUserName(USER_NAME);

        assertEquals(1, users.size());
        final var retrieved = users.get(0);

        assertEquals(USER_NAME, retrieved.getUserName());
    }
}