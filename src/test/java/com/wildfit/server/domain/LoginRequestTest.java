package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginRequestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(LoginRequest.class).verify();
    }

    @Test
    void builder() {
        final var request = LoginRequest.builder().withEmail("p").build();
        assertEquals("p", request.email());
    }
}