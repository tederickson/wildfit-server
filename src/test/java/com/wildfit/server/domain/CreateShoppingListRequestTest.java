package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CreateShoppingListRequestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CreateShoppingListRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = CreateShoppingListRequest.builder()
                .withUuid("unique user id")
                .build();
        assertEquals("unique user id", request.uuid());
        assertNull(request.mealId());
    }
}