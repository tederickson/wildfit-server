package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingListDigestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ShoppingListDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var digest = ShoppingListDigest.builder()
                .withId(142143L)
                .withUuid("APP")
                .withItems(List.of())
                .build();

        assertEquals(142143L, digest.id());
        assertEquals("APP", digest.uuid());
        assertTrue(digest.items().isEmpty());
    }
}