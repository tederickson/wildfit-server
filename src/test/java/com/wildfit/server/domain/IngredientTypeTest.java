package com.wildfit.server.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTypeTest {

    @Test
    void getDescription() {
        assertEquals("Deli & Specialty Cheese", IngredientType.DELI.getDescription());
    }
}