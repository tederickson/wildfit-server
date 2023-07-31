package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.UpdateIngredientRequest;
import com.wildfit.server.model.RecipeIngredient;
import com.wildfit.server.model.RecipeIngredientType;

public class RecipeIngredientMapper {
    private RecipeIngredientMapper() {
    }

    public static IngredientDigest map(RecipeIngredient recipeIngredient) {
        final var recipeIngredientType = RecipeIngredientType.findByCode(recipeIngredient.getIngredientType());

        return IngredientDigest.builder()
                               .withDescription(recipeIngredient.getDescription())
                               .withFoodName(recipeIngredient.getFoodName())
                               .withId(recipeIngredient.getId())
                               .withIngredientServingQty(recipeIngredient.getIngredientServingQty())
                               .withIngredientServingUnit(recipeIngredient.getIngredientServingUnit())
                               .withIngredientType(recipeIngredientType.toIngredientType())
                               .withInstructionGroupId(recipeIngredient.getInstructionGroupId())
                               .withRecipeId(recipeIngredient.getRecipeId())
                               .withServingQty(recipeIngredient.getServingQty())
                               .withServingUnit(recipeIngredient.getServingUnit())
                               .build();
    }

    public static RecipeIngredient create(IngredientDigest ingredientDigest, Long recipeId, Long recipeGroupId) {
        final var recipeIngredientType = RecipeIngredientType.map(ingredientDigest.getIngredientType());

        return RecipeIngredient.builder()
                               .withDescription(ingredientDigest.getDescription())
                               .withFoodName(ingredientDigest.getFoodName().trim().toLowerCase())
                               .withIngredientServingQty(ingredientDigest.getIngredientServingQty())
                               .withIngredientServingUnit(ingredientDigest.getIngredientServingUnit())
                               .withIngredientType(recipeIngredientType.getCode())
                               .withInstructionGroupId(recipeGroupId)
                               .withRecipeId(recipeId)
                               .withServingQty(ingredientDigest.getServingQty())
                               .withServingUnit(ingredientDigest.getServingUnit())
                               .build();
    }

    public static void update(UpdateIngredientRequest request, RecipeIngredient recipeIngredient) {
        recipeIngredient.setDescription(request.getDescription());
        recipeIngredient.setIngredientServingQty(request.getIngredientServingQty());
        recipeIngredient.setIngredientServingUnit(request.getIngredientServingUnit());

        final var recipeIngredientType = RecipeIngredientType.map(request.getIngredientType());
        recipeIngredient.setIngredientType(recipeIngredientType.getCode());
    }
}
