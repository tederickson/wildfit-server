package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DeleteUserHandlerIT extends CommonHandlerTest {

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> DeleteUserHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.deleteUser(null));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> userService.deleteUser("-14L"));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(PASSWORD)
                .withUuid(UUID.randomUUID().toString())
                .withEmail(EMAIL).build();

        final var saved = userRepository.save(user);
        assertNotNull(saved);

        userService.deleteUser(saved.getUuid());

        final var users = userRepository.findByEmail(EMAIL);
        assertTrue(users.isEmpty());
    }
}