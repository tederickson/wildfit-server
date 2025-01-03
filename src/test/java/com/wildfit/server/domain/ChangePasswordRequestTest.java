package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangePasswordRequestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ChangePasswordRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = ChangePasswordRequest.builder().withPassword("p").build();
        assertEquals("p", request.password());
    }
}