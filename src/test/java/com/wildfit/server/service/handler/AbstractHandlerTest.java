package com.wildfit.server.service.handler;

import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractHandlerTest {
    protected static final String PASSWORD = "Super2023!";
    protected static final String EMAIL = "ted.erickson@comcast.net";
    protected static final String NAME = "Ted Erickson";

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }
}
