package com.wildfit.server.service;

import com.wildfit.server.domain.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReferenceServiceTest {
    private ReferenceService service;

    @BeforeEach
    void setUp() {
        service = new ReferenceService();
    }

    @Test
    void getIngredientTypes() {
        final var response = service.getIngredientTypes();

        assertEquals(IngredientType.values().length, response.size());

        for (var reference : response) {
            IngredientType ingredientType = IngredientType.valueOf(reference.type());

            assertEquals(ingredientType.getDescription(), reference.description());
        }
    }
}