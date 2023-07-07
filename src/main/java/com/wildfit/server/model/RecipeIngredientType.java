package com.wildfit.server.model;

import com.wildfit.server.domain.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecipeIngredientType {
    MEAT("M"), PRODUCE("P"), DAIRY("D"), SPICE("S"), NONE("N");

    final String code;

    public static RecipeIngredientType findByCode(String code) {
        for (var value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return NONE;
    }

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
