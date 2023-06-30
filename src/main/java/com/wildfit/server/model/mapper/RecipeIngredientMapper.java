package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.model.RecipeIngredient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecipeIngredientMapper {
    private RecipeIngredientMapper() {
    }

    public static IngredientDigest map(RecipeIngredient recipeIngredient) {
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
                .withInstructionGroupId(recipeIngredient.getInstructionGroupId())
                .withIron(recipeIngredient.getIron())
                .withMetricQty(recipeIngredient.getMetricQty())
                .withMetricUom(recipeIngredient.getMetricUom())
                .withNixBrandId(recipeIngredient.getNixBrandId())
                .withNixBrandName(recipeIngredient.getNixBrandName())
                .withNixItemId(recipeIngredient.getNixItemId())
                .withPhosphorus(recipeIngredient.getPhosphorus())
                .withPhoto(PhotoDigestMapper.map(recipeIngredient.getPhoto_thumbnail()))
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
        return RecipeIngredient.builder()
                .withAddedSugars(ingredientDigest.getAddedSugars())
                .withBrandName(ingredientDigest.getBrandName())
                .withBrandNameItemName(ingredientDigest.getBrandNameItemName())
                .withCalcium(ingredientDigest.getCalcium())
                .withCalories(ingredientDigest.getCalories())
                .withCholesterol(ingredientDigest.getCholesterol())
                .withDescription(ingredientDigest.getDescription())
                .withDietaryFiber(ingredientDigest.getDietaryFiber())
                .withFoodName(ingredientDigest.getFoodName())
                // .withId(ingredientDigest.getId())
                .withIngredientServingQty(ingredientDigest.getIngredientServingQty())
                .withIngredientServingUnit(ingredientDigest.getIngredientServingUnit())
                .withInstructionGroupId(recipeGroupId)
                .withIron(ingredientDigest.getIron())
                .withMetricQty(ingredientDigest.getMetricQty())
                .withMetricUom(ingredientDigest.getMetricUom())
                .withNixBrandId(ingredientDigest.getNixBrandId())
                .withNixBrandName(ingredientDigest.getNixBrandName())
                .withNixItemId(ingredientDigest.getNixItemId())
                .withPhosphorus(ingredientDigest.getPhosphorus())
                .withPhoto_thumbnail(ingredientDigest.getPhoto() == null ? null : ingredientDigest.getPhoto().getThumb())
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

    public static RecipeIngredient update(IngredientDigest ingredientDigest, RecipeIngredient recipeIngredient) {
        recipeIngredient.setAddedSugars(ingredientDigest.getAddedSugars());
        recipeIngredient.setBrandName(ingredientDigest.getBrandName());
        recipeIngredient.setBrandNameItemName(ingredientDigest.getBrandNameItemName());
        recipeIngredient.setCalcium(ingredientDigest.getCalcium());
        recipeIngredient.setCalories(ingredientDigest.getCalories());
        recipeIngredient.setCholesterol(ingredientDigest.getCholesterol());
        recipeIngredient.setDescription(ingredientDigest.getDescription());
        recipeIngredient.setDietaryFiber(ingredientDigest.getDietaryFiber());
        recipeIngredient.setFoodName(ingredientDigest.getFoodName());
        // recipeIngredient.setId(ingredientDigest.getId())
        recipeIngredient.setIngredientServingQty(ingredientDigest.getIngredientServingQty());
        recipeIngredient.setIngredientServingUnit(ingredientDigest.getIngredientServingUnit());
        // recipeIngredient.setInstructionGroupId(ingredientDigest.getInstructionGroupId());
        recipeIngredient.setIron(ingredientDigest.getIron());
        recipeIngredient.setMetricQty(ingredientDigest.getMetricQty());
        recipeIngredient.setMetricUom(ingredientDigest.getMetricUom());
        recipeIngredient.setNixBrandId(ingredientDigest.getNixBrandId());
        recipeIngredient.setNixBrandName(ingredientDigest.getNixBrandName());
        recipeIngredient.setNixItemId(ingredientDigest.getNixItemId());
        recipeIngredient.setPhosphorus(ingredientDigest.getPhosphorus());
        recipeIngredient.setPhoto_thumbnail(ingredientDigest.getPhoto() == null ? null : ingredientDigest.getPhoto().getThumb());
        recipeIngredient.setPotassium(ingredientDigest.getPotassium());
        recipeIngredient.setProtein(ingredientDigest.getProtein());
        // recipeIngredient.setRecipeId(ingredientDigest.getRecipeId())
        recipeIngredient.setSaturatedFat(ingredientDigest.getSaturatedFat());
        recipeIngredient.setServingQty(ingredientDigest.getServingQty());
        recipeIngredient.setServingUnit(ingredientDigest.getServingUnit());
        recipeIngredient.setServingWeightGrams(ingredientDigest.getServingWeightGrams());
        recipeIngredient.setSodium(ingredientDigest.getSodium());
        recipeIngredient.setSugars(ingredientDigest.getSugars());
        recipeIngredient.setTotalCarbohydrate(ingredientDigest.getTotalCarbohydrate());
        recipeIngredient.setTotalFat(ingredientDigest.getTotalFat());
        recipeIngredient.setTransFattyAcid(ingredientDigest.getTransFattyAcid());
        recipeIngredient.setVitaminD(ingredientDigest.getVitaminD());

        return recipeIngredient;
    }
}
