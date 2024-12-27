package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstructionDigestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(InstructionDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = InstructionDigest.builder().withInstruction("p").build();
        assertEquals("p", request.instruction());
    }
}