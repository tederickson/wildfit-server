package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.SearchFoodItemDigest;
import com.wildfit.server.model.SearchedFoodItem;

public final class SearchFoodItemDigestMapper {
    private SearchFoodItemDigestMapper() {
    }

    public static SearchFoodItemDigest map(SearchedFoodItem food) {
        final var builder = SearchFoodItemDigest.builder();

        if (food != null) {
            builder.withFoodName(food.getFood_name())
                    .withBrandName(food.getBrand_name())
                    .withBrandNameItemName(food.getBrand_name_item_name())
                    .withServingQty(food.getServing_qty())
                    .withServingUnit(food.getServing_unit())
                    .withCalories(food.getNf_calories())
                    .withNixBrandName(food.getNix_brand_name())
                    .withNixBrandId(food.getNix_brand_id())
                    .withNixItemId(food.getNix_item_id())
                    .withPhoto(PhotoDigestMapper.map(food.getPhoto()));
        }
        return builder.build();
    }
}
