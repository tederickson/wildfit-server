package com.wildfit.server.model;

import com.wildfit.server.domain.IngredientType;
import lombok.Getter;

@Getter
public enum RecipeIngredientType {
    BREAD,
    CAN,
    DAIRY,
    DELI,
    INTERNATIONAL,
    MEAT,
    NUT,
    OIL,
    PRODUCE,
    SPICE,

    NONE;

    public static RecipeIngredientType map(IngredientType ingredientType) {
        if (ingredientType == null) {
            return NONE;
        }
        return valueOf(ingredientType.name());
    }

    public IngredientType toIngredientType() {
        return IngredientType.valueOf(name());
    }
}
