package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserRequestTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CreateUserRequest.class).verify();
    }

    @Test
    void builder() {
        final var request = CreateUserRequest.builder()
                .withEmail("email")
                .withPassword("p")
                .build();
        assertEquals("email", request.email());
        assertEquals("p", request.password());
    }
}