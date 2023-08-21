package com.wildfit.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IngredientTypeTest {

    @Test
    void getDescription() {
        assertEquals("Deli & Specialty Cheese", IngredientType.DELI.getDescription());
    }
}