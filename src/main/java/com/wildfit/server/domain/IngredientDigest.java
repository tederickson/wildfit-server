package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record IngredientDigest(
        Long id,

        String foodName,
        String description,

        Float ingredientServingQty,
        String ingredientServingUnit,

        IngredientType ingredientType) {
}
