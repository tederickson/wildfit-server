package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstructionDigestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(InstructionDigest.class).verify();
    }

    @Test
    void builder() {
        final var request = InstructionDigest.builder().withInstruction("p").build();
        assertEquals("p", request.instruction());
    }
}