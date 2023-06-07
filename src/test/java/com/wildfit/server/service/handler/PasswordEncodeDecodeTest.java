package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordEncodeDecodeTest {

    @Test
    void matches() {
        final var password = "Zimbabwe213!@#$%^&*()";
        final var encodedPassword = PasswordEncodeDecode.encode(password);

        assertNotEquals(password, encodedPassword);
        assertTrue(PasswordEncodeDecode.matches(password, encodedPassword));
    }
}