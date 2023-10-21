package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.PhotoDigest;
import com.wildfit.server.model.FoodItems;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class FoodItemDigestMapperTest {

    public static FoodItems getFoodItems(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            return mapper.readValue(in, FoodItems.class);
        }
    }

    @Test
    void null_FoodItem() {
        final var foodItemDigest = FoodItemDigestMapper.mapFoodItem(null);
        assertNotNull(foodItemDigest);
        assertNull(foodItemDigest.getFoodName());
    }

    @Test
    void null_SearchedFoodItem() {
        final var foodItemDigest = FoodItemDigestMapper.mapSearchedFoodItem(null);
        assertNotNull(foodItemDigest);
        assertNull(foodItemDigest.getFoodName());
    }

    @Test
    void mapFoodItem() throws IOException {
        final var foodItems = getFoodItems("search-item-kerrygold-barcode.json");

        assertEquals(1, foodItems.getFoods().length);

        final var foodItemDigest = FoodItemDigestMapper.mapFoodItem(foodItems.getFoods()[0]);
        final var photo = PhotoDigest.builder()
                                     .withThumb(
                                             "https://assets.syndigo.com/292e4d9d-0ab6-4084-9979-83c6ace9f7b1")
                                     .build();
        final var expected = FoodItemDigest.builder()
                                           .withFoodName("Butter, Pure Irish, Unsalted")
                                           .withBrandName("Kerrygold")
                                           .withServingQty(1f)
                                           .withServingUnit("tbsp")
                                           .withServingWeightGrams(14f)
                                           .withMetricQty(14f)
                                           .withMetricUom("g")
                                           .withCalories(100.0f)
                                           .withTotalFat(12.0f)
                                           .withSaturatedFat(8.0f)
                                           .withCholesterol(30.0f)
                                           .withSodium(0.0f)
                                           .withTotalCarbohydrate(0.0f)
                                           .withProtein(0.0f)
                                           .withPhosphorus(0.0f)
                                           .withNixBrandName("Kerrygold")
                                           .withNixBrandId("51db37b7176fe9790a8989b4")
                                           .withNixItemId("52a15041d122497b50000a75")
                                           .withPhoto(photo)
                                           .build();
        assertEquals(expected, foodItemDigest);
    }

    @Test
    void mapManyNutrients() throws IOException {
        final var foodItems = getFoodItems("search-item-carb-balance-tortilla-nix_item_id.json");

        assertEquals(1, foodItems.getFoods().length);

        final var foodItemDigest = FoodItemDigestMapper.mapFoodItem(foodItems.getFoods()[0]);

        assertNull(foodItemDigest.getPhosphorus());
        assertEquals(50, foodItemDigest.getPotassium());
    }
}