package com.wildfit.server.domain;

import lombok.Builder;

@Builder(setterPrefix = "with", toBuilder = true)
public record ShoppingListItemDigest(
        Long id,

        String foodName,

        Float totalQuantity,
        String unit,

        IngredientType ingredientType,
        boolean purchased) {
}
