package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.wildfit.server.domain.IngredientType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RecipeIngredientTypeTest {

    @Test
    void toIngredientType() {
        for (var enm : IngredientType.values()) {
            RecipeIngredientType.valueOf(enm.name());
        }
        for (var enm : RecipeIngredientType.values()) {
            assertNotNull(enm.toIngredientType());
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" Spring ", "apple"})
    void notFound(String code) {
        assertEquals(RecipeIngredientType.NONE, RecipeIngredientType.findByCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"M", "S"})
    void findByCode(String code) {
        assertNotEquals(RecipeIngredientType.NONE, RecipeIngredientType.findByCode(code));
    }

    @Test
    void distinctCodes() {
        final var values = RecipeIngredientType.values();
        final var codes = Arrays.stream(values).map(RecipeIngredientType::getCode).collect(Collectors.toSet());
        assertEquals(values.length, codes.size());
    }
}