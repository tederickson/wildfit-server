package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.domain.IngredientType;
import org.junit.jupiter.api.Test;

class RecipeIngredientTypeTest {

    @Test
    void toIngredientType() {
        for (var enm : RecipeIngredientType.values()) {
            assertNotNull(enm.toIngredientType());
        }
    }

    @Test
    void map() {
        for (var enm : IngredientType.values()) {
            assertNotNull(RecipeIngredientType.map(enm));
        }
        assertEquals(RecipeIngredientType.NONE, RecipeIngredientType.map(null));
    }

    @Test
    void ensureEnumFitsInDatabase() {
        for (var enm : RecipeIngredientType.values()) {
            assertTrue(enm.name().length() < 20, enm.toString());
        }
    }
}