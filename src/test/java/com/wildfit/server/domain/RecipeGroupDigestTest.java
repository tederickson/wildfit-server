package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeGroupDigestTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(RecipeGroupDigest.class).verify();
    }

    @Test
    void builder() {
        final var request = RecipeGroupDigest.builder().withName("p").build();
        assertEquals("p", request.name());
    }
}