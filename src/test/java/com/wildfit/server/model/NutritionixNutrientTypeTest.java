package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class NutritionixNutrientTypeTest {

    @Test
    void getUsdaTag() {
        for (var enm : NutritionixNutrientType.values()) {
            assertEquals(enm.name(), enm.getUsdaTag(), enm.toString());
        }
    }

    @Test
    void getBulkCsvField() {
        final var distinct = java.util.Arrays.stream(NutritionixNutrientType.values())
                .map(NutritionixNutrientType::getBulkCsvField)
                .collect(Collectors.toSet());
        assertEquals(NutritionixNutrientType.values().length, distinct.size());
    }
}