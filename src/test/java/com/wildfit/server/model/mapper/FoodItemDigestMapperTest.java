package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.nio.file.Files;

import com.wildfit.server.domain.PhotoDigest;
import com.wildfit.server.model.FoodItems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

@JsonTest
class FoodItemDigestMapperTest {

    @Value("classpath:search-item-kerrygold-barcode.json")
    Resource jsonFile;

    @Autowired
    private JacksonTester<FoodItems> jacksonTester;

    @Test
    void nullParameters() {
        final var foodItemDigest = FoodItemDigestMapper.map(null);
        assertNotNull(foodItemDigest);
        assertNull(foodItemDigest.getFoodName());
    }

    @Test
    void readResponse() throws IOException {
        final var json = Files.readString(jsonFile.getFile().toPath());
        final var foodItems = jacksonTester.parse(json).getObject();

        assertEquals(1, foodItems.getFoods().length);

        final var foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        final var expected = com.wildfit.server.domain.FoodItemDigest.builder()
                .withFoodName("Butter, Pure Irish, Unsalted")
                .withBrandName("Kerrygold")
                .withServingQty(1)
                .withServingUnit("tbsp")
                .withServingWeightGrams(14)
                .withMetricQty(14)
                .withMetricUom("g")
                .withCalories(100)
                .withTotalFat(12)
                .withSaturatedFat(8)
                .withCholesterol(30)
                .withSodium(0)
                .withTotalCarbohydrate(0)
                .withDietaryFiber(null)
                .withSugars(null)
                .withProtein(0)
                .withPotassium(null)
                .withNixBrandName("Kerrygold")
                .withNixBrandId("51db37b7176fe9790a8989b4")
                .withNixItemId("52a15041d122497b50000a75")
                .withPhoto(PhotoDigest.builder()
                        .withThumb("https://assets.syndigo.com/292e4d9d-0ab6-4084-9979-83c6ace9f7b1").build())
                .build();
        assertEquals(expected, foodItemDigest);
    }
}