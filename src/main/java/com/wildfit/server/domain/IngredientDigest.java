package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class IngredientDigest {
    private Long id;

    private String foodName;
    private String description;

    private Float ingredientServingQty;
    private String ingredientServingUnit;

    private IngredientType ingredientType;
}
