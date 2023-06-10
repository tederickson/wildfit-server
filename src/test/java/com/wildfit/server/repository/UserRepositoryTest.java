package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    private static final String PASSWORD = "encodedPassword";
    private static final String EMAIL = "bob@somewhere.com";

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        final var users = userRepository.findByEmail(PASSWORD);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void findByEmail_withUser() {
        final var user = User.builder()
                .withStatus(UserStatus.CREATE.getCode())
                .withCreateDate(new Date())
                .withConfirmCode("confcode")
                .withPassword(PASSWORD)
                .withEmail(EMAIL).build();
        final var saved = userRepository.save(user);
        assertNotNull(saved);

        final var users = userRepository.findByEmail(EMAIL);

        assertEquals(1, users.size());
        final var retrieved = users.get(0);

        assertEquals(EMAIL, retrieved.getEmail());
        assertEquals(UserStatus.CREATE, retrieved.getUserStatus());
    }
}