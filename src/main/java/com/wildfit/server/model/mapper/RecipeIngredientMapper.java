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
                .withAddedSugars(recipeIngredient.getAddedSugars())
                .withBrandName(recipeIngredient.getBrandName())
                .withBrandNameItemName(recipeIngredient.getBrandNameItemName())
                .withCalcium(recipeIngredient.getCalcium())
                .withCalories(recipeIngredient.getCalories())
                .withCholesterol(recipeIngredient.getCholesterol())
                .withDescription(recipeIngredient.getDescription())
                .withDietaryFiber(recipeIngredient.getDietaryFiber())
                .withFoodName(recipeIngredient.getFoodName())
                .withId(recipeIngredient.getId())
                .withIngredientServingQty(recipeIngredient.getIngredientServingQty())
                .withIngredientServingUnit(recipeIngredient.getIngredientServingUnit())
                .withIngredientType(recipeIngredientType.toIngredientType())
                .withInstructionGroupId(recipeIngredient.getInstructionGroupId())
                .withIron(recipeIngredient.getIron())
                .withMetricQty(recipeIngredient.getMetricQty())
                .withMetricUom(recipeIngredient.getMetricUom())
                .withNixBrandId(recipeIngredient.getNixBrandId())
                .withNixBrandName(recipeIngredient.getNixBrandName())
                .withNixItemId(recipeIngredient.getNixItemId())
                .withPhosphorus(recipeIngredient.getPhosphorus())
                .withPhoto(PhotoDigestMapper.map(recipeIngredient.getPhotoThumbnail()))
                .withPotassium(recipeIngredient.getPotassium())
                .withProtein(recipeIngredient.getProtein())
                .withRecipeId(recipeIngredient.getRecipeId())
                .withSaturatedFat(recipeIngredient.getSaturatedFat())
                .withServingQty(recipeIngredient.getServingQty())
                .withServingUnit(recipeIngredient.getServingUnit())
                .withServingWeightGrams(recipeIngredient.getServingWeightGrams())
                .withSodium(recipeIngredient.getSodium())
                .withSugars(recipeIngredient.getSugars())
                .withTotalCarbohydrate(recipeIngredient.getTotalCarbohydrate())
                .withTotalFat(recipeIngredient.getTotalFat())
                .withTransFattyAcid(recipeIngredient.getTransFattyAcid())
                .withVitaminD(recipeIngredient.getVitaminD())
                .build();
    }

    public static RecipeIngredient create(IngredientDigest ingredientDigest, Long recipeId, Long recipeGroupId) {
        final var recipeIngredientType = RecipeIngredientType.map(ingredientDigest.getIngredientType());

        return RecipeIngredient.builder()
                .withAddedSugars(ingredientDigest.getAddedSugars())
                .withBrandName(ingredientDigest.getBrandName())
                .withBrandNameItemName(ingredientDigest.getBrandNameItemName())
                .withCalcium(ingredientDigest.getCalcium())
                .withCalories(ingredientDigest.getCalories())
                .withCholesterol(ingredientDigest.getCholesterol())
                .withDescription(ingredientDigest.getDescription())
                .withDietaryFiber(ingredientDigest.getDietaryFiber())
                .withFoodName(ingredientDigest.getFoodName().trim().toLowerCase())
                // .withId(ingredientDigest.getId())
                .withIngredientServingQty(ingredientDigest.getIngredientServingQty())
                .withIngredientServingUnit(ingredientDigest.getIngredientServingUnit())
                .withIngredientType(recipeIngredientType.getCode())
                .withInstructionGroupId(recipeGroupId)
                .withIron(ingredientDigest.getIron())
                .withMetricQty(ingredientDigest.getMetricQty())
                .withMetricUom(ingredientDigest.getMetricUom())
                .withNixBrandId(ingredientDigest.getNixBrandId())
                .withNixBrandName(ingredientDigest.getNixBrandName())
                .withNixItemId(ingredientDigest.getNixItemId())
                .withPhosphorus(ingredientDigest.getPhosphorus())
                .withPhotoThumbnail(ingredientDigest.getPhoto() == null ? null : ingredientDigest.getPhoto().getThumb())
                .withPotassium(ingredientDigest.getPotassium())
                .withProtein(ingredientDigest.getProtein())
                .withRecipeId(recipeId)
                .withSaturatedFat(ingredientDigest.getSaturatedFat())
                .withServingQty(ingredientDigest.getServingQty())
                .withServingUnit(ingredientDigest.getServingUnit())
                .withServingWeightGrams(ingredientDigest.getServingWeightGrams())
                .withSodium(ingredientDigest.getSodium())
                .withSugars(ingredientDigest.getSugars())
                .withTotalCarbohydrate(ingredientDigest.getTotalCarbohydrate())
                .withTotalFat(ingredientDigest.getTotalFat())
                .withTransFattyAcid(ingredientDigest.getTransFattyAcid())
                .withVitaminD(ingredientDigest.getVitaminD())
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
