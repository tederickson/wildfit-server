package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.model.FoodItem;

public final class FoodItemDigestMapper {
    private FoodItemDigestMapper() {
    }

    public static FoodItemDigest map(FoodItem food) {
        final var builder = FoodItemDigest.builder();

        if (food != null) {
            builder.withFoodName(food.getFood_name())
                    .withBrandName(food.getBrand_name())
                    .withBrandNameItemName(food.getBrand_name_item_name())
                    .withServingQty(food.getServing_qty())
                    .withServingUnit(food.getServing_unit())
                    .withServingWeightGrams(food.getServing_weight_grams())
                    .withMetricQty(food.getNf_metric_qty())
                    .withMetricUom(food.getNf_metric_uom())
                    .withCalories(food.getNf_calories())
                    .withTotalFat(food.getNf_total_fat())
                    .withSaturatedFat(food.getNf_saturated_fat())
                    .withCholesterol(food.getNf_cholesterol())
                    .withSodium(food.getNf_sodium())
                    .withTotalCarbohydrate(food.getNf_total_carbohydrate())
                    .withDietaryFiber(food.getNf_dietary_fiber())
                    .withSugars(food.getNf_sugars())
                    .withProtein(food.getNf_protein())
                    .withPotassium(food.getNf_potassium())
                    //  .withNf_p(food.getNf_p())
                    .withNixBrandName(food.getNix_brand_name())
                    .withNixBrandId(food.getNix_brand_id())
                    .withNixItemId(food.getNix_item_id())
                    .withPhoto(PhotoDigestMapper.map(food.getPhoto()));
        }
        return builder.build();
    }
}
