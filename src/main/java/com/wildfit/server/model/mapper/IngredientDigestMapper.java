package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.IngredientDigest;

public class IngredientDigestMapper {
    private IngredientDigestMapper() {
    }

    public static IngredientDigest create(FoodItemDigest foodItemDigest,
                                          String description,
                                          Float ingredientServingQty,
                                          String ingredientServingUnit) {
        return IngredientDigest.builder()
                .withIngredientServingQty(ingredientServingQty)
                .withIngredientServingUnit(ingredientServingUnit)
                .withDescription(description)

                .withAddedSugars(foodItemDigest.getAddedSugars())
                .withBrandName(foodItemDigest.getBrandName())
                .withBrandNameItemName(foodItemDigest.getBrandNameItemName())
                .withCalcium(foodItemDigest.getCalcium())
                .withCalories(foodItemDigest.getCalories())
                .withCholesterol(foodItemDigest.getCholesterol())
                .withDietaryFiber(foodItemDigest.getDietaryFiber())
                .withFoodName(foodItemDigest.getFoodName())
                .withIron(foodItemDigest.getIron())
                .withMetricQty(foodItemDigest.getMetricQty())
                .withMetricUom(foodItemDigest.getMetricUom())
                .withNixBrandId(foodItemDigest.getNixBrandId())
                .withNixBrandName(foodItemDigest.getNixBrandName())
                .withNixItemId(foodItemDigest.getNixItemId())
                .withPhosphorus(foodItemDigest.getPhosphorus())
                .withPhoto(foodItemDigest.getPhoto())
                .withPotassium(foodItemDigest.getPotassium())
                .withProtein(foodItemDigest.getProtein())
                .withSaturatedFat(foodItemDigest.getSaturatedFat())
                .withServingQty(foodItemDigest.getServingQty())
                .withServingUnit(foodItemDigest.getServingUnit())
                .withServingWeightGrams(foodItemDigest.getServingWeightGrams())
                .withSodium(foodItemDigest.getSodium())
                .withSugars(foodItemDigest.getSugars())
                .withTotalCarbohydrate(foodItemDigest.getTotalCarbohydrate())
                .withTotalFat(foodItemDigest.getTotalFat())
                .withTransFattyAcid(foodItemDigest.getTransFattyAcid())
                .withVitaminD(foodItemDigest.getVitaminD())
                .build();
    }
}
