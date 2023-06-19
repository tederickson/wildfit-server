package com.wildfit.server.repository;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractRepositoryTest {
    protected static final String PASSWORD = "Super2023!";
    protected static final String EMAIL = "tederickson35@gmail.com";

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }
}
