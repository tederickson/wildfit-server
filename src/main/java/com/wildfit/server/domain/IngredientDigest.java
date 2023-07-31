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
    private long id;
    private long recipeId;
    private long instructionGroupId;

    private String foodName;
    private String description;

    private Float servingQty;
    private String servingUnit;
    private Float ingredientServingQty;
    private String ingredientServingUnit;

    private IngredientType ingredientType;
}
