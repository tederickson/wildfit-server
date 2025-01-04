package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CreateMealRequestTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CreateMealRequest.class).verify();
    }

    @Test
    void builder() {
        final var request = CreateMealRequest.builder()
                .withUuid("unique user id")
                .build();
        assertEquals("unique user id", request.uuid());
        assertNull(request.recipeIds());
    }
}