package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.IngredientType;

public class IngredientDigestMapper {
    private IngredientDigestMapper() {
    }

    public static IngredientDigest create(FoodItemDigest foodItemDigest,
                                          String description,
                                          Float ingredientServingQty,
                                          String ingredientServingUnit,
                                          IngredientType ingredientType) {
        return IngredientDigest.builder()
                               .withIngredientServingQty(ingredientServingQty)
                               .withIngredientServingUnit(ingredientServingUnit)
                               .withDescription(description)
                               .withFoodName(foodItemDigest.getFoodName())
                               .withIngredientType(ingredientType)
                               .withServingQty(foodItemDigest.getServingQty())
                               .withServingUnit(foodItemDigest.getServingUnit())
                               .build();
    }
}
