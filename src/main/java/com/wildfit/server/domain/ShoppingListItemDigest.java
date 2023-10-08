package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class ShoppingListItemDigest {
    private Long id;

    private String foodName;

    private Float totalQuantity;
    private String unit;

    private IngredientType ingredientType;
    private boolean purchased;
}
