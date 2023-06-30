package com.wildfit.server.service.handler;

import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractHandlerTest {
    protected static final String PASSWORD = "SuperCaliTest2023!"; // Must be 8 - 20 characters
    protected static final String EMAIL = "tederickson35@gmail.com";
    protected static final String NAME = "Ted Erickson";

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }
}
