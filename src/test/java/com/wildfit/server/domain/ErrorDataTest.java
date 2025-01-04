package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorDataTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ErrorData.class).verify();
    }

    @Test
    void builder() {
        final var request = ErrorData.builder().withMessage("p").build();
        assertEquals("p", request.message());
    }
}