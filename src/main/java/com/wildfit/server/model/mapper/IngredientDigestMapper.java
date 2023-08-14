package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.IngredientType;
import org.apache.commons.lang3.StringUtils;

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
                               .build();
    }

    public static com.wildfit.server.model.Ingredient createIngredient(IngredientDigest ingredientDigest) {
        return new com.wildfit.server.model.Ingredient()
                .setId(ingredientDigest.getId())
                .setFoodName(StringUtils.trimToNull(ingredientDigest.getFoodName()))
                .setDescription(StringUtils.trimToNull(ingredientDigest.getDescription()))
                .setIngredientServingQty(ingredientDigest.getIngredientServingQty())
                .setIngredientServingUnit(StringUtils.trimToNull(ingredientDigest.getIngredientServingUnit()))
                .setIngredientType(getIngredientType(ingredientDigest));
    }

    private static IngredientType getIngredientType(IngredientDigest ingredientDigest) {
        return ingredientDigest.getIngredientType() == null ? IngredientType.NONE :
                ingredientDigest.getIngredientType();
    }

    public static IngredientDigest createIngredient(com.wildfit.server.model.Ingredient ingredient) {
        return IngredientDigest.builder()
                               .withId(ingredient.getId())
                               .withFoodName(ingredient.getFoodName())
                               .withDescription(ingredient.getDescription())
                               .withIngredientServingQty(ingredient.getIngredientServingQty())
                               .withIngredientServingUnit(ingredient.getIngredientServingUnit())
                               .withIngredientType(ingredient.getIngredientType())
                               .build();
    }

    public static com.wildfit.server.model.Ingredient updateIngredient(
            com.wildfit.server.model.Ingredient ingredient,
            IngredientDigest ingredientDigest) {
        return ingredient
                .setFoodName(StringUtils.trimToNull(ingredientDigest.getFoodName()))
                .setDescription(StringUtils.trimToNull(ingredientDigest.getDescription()))
                .setIngredientServingQty(ingredientDigest.getIngredientServingQty())
                .setIngredientServingUnit(StringUtils.trimToNull(ingredientDigest.getIngredientServingUnit()))
                .setIngredientType(getIngredientType(ingredientDigest));
    }
}
