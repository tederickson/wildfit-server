package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserResponseTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CreateUserResponse.class).verify();
    }

    @Test
    void builder() {
        final var request = CreateUserResponse.builder().withEmail("p").build();
        assertEquals("p", request.email());
    }
}