package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterUserResponseTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(RegisterUserResponse.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = RegisterUserResponse.builder().withMessage("p").build();
        assertEquals("p", request.message());
    }
}