package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReferenceDigestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ReferenceDigest.class).verify();
    }

    @Test
    void builder() {
        final var request = ReferenceDigest.builder()
                .withType("type")
                .withDescription("description")
                .build();
        assertEquals("type", request.type());
        assertEquals("description", request.description());
    }
}