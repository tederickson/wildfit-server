package com.wildfit.server.model;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NutritionixNutrientTypeTest {

    @Test
    void getUsdaTag() {
        for (var enm : NutritionixNutrientType.values()) {
            assertEquals(enm.name(), enm.getUsdaTag(), enm.toString());
        }
    }

    @Test
    void getUnit() {
        for (var enm : NutritionixNutrientType.values()) {
            assertNotNull(enm.getUnit(), enm.toString());
        }
    }

    @Test
    void getBulkCsvField() {
        final var distinct = java.util.Arrays.stream(NutritionixNutrientType.values())
                .map(NutritionixNutrientType::getBulkCsvField)
                .collect(Collectors.toSet());
        assertEquals(NutritionixNutrientType.values().length, distinct.size());
    }

    @Test
    void uniqueAttributeId() {
        final var distinct = java.util.Arrays.stream(NutritionixNutrientType.values())
                .map(NutritionixNutrientType::getAttrId)
                .collect(Collectors.toSet());
        assertEquals(NutritionixNutrientType.values().length, distinct.size());
    }

    @Test
    void uniqueDescription() {
        final var distinct = java.util.Arrays.stream(NutritionixNutrientType.values())
                .map(NutritionixNutrientType::getDescription)
                .collect(Collectors.toSet());
        assertEquals(NutritionixNutrientType.values().length, distinct.size());
    }
}