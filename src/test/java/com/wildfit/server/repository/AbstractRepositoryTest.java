package com.wildfit.server.repository;

import java.time.LocalDate;
import java.util.UUID;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractRepositoryTest {
    protected static final String PASSWORD = "Super2023!";
    protected static final String EMAIL = "tederickson35@gmail.com";
    protected static final User USER = User.builder()
                                           .withStatus(UserStatus.FREE.getCode())
                                           .withCreateDate(LocalDate.now())
                                           .withPassword(PASSWORD)
                                           .withUuid(UUID.randomUUID().toString())
                                           .withEmail(EMAIL).build();

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        final var users = userRepository.findByEmail(EMAIL);

        userRepository.deleteAll(users);
    }
}
