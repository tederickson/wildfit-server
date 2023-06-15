package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.model.FoodItem;

public final class FoodItemDigestMapper {
    private FoodItemDigestMapper() {
    }

    public static FoodItemDigest map(FoodItem food) {
        return FoodItemDigest.builder()
                .withFoodName(food.getFood_name())
                .withBrandName(food.getBrand_name())
                .withServingQty(food.getServing_qty())
                .withServingUnit(food.getServing_unit())
                .withServingWeightGrams(food.getServing_weight_grams())
                .withMetricQty(food.getNf_metric_qty())
                .withMetricUom(food.getNf_metric_uom())
                .withNf_calories(food.getNf_calories())
                .withNf_total_fat(food.getNf_total_fat())
                .withNf_saturated_fat(food.getNf_saturated_fat())
                .withNf_cholesterol(food.getNf_cholesterol())
                .withNf_sodium(food.getNf_sodium())
                .withNf_total_carbohydrate(food.getNf_total_carbohydrate())
                .withNf_dietary_fiber(food.getNf_dietary_fiber())
                .withNf_sugars(food.getNf_sugars())
                .withNf_protein(food.getNf_protein())
                .withNf_potassium(food.getNf_potassium())
                //  .withNf_p(food.getNf_p())
                .withNix_brand_name(food.getNix_brand_name())
                .withNix_brand_id(food.getNix_brand_id())
                .withNix_item_id(food.getNix_item_id())
                .build();
    }
}
