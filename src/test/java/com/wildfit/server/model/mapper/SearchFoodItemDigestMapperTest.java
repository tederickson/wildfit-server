package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.nio.file.Files;

import com.wildfit.server.model.SearchedFoodItems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

@JsonTest
class SearchFoodItemDigestMapperTest {
    @Value("classpath:search-instant-grilled-cheese.json")
    Resource grilledCheeseSearch;

    @Autowired
    private JacksonTester<SearchedFoodItems> jacksonTester;

    @Test
    void nullParameters() {
        final var searchFoodItemDigest = SearchFoodItemDigestMapper.map(null);
        assertNotNull(searchFoodItemDigest);
        assertNull(searchFoodItemDigest.getFoodName());
    }

    @Test
    void readResponse() throws IOException {
        String json = Files.readString(grilledCheeseSearch.getFile().toPath());
        final var searchedFoodItems = jacksonTester.parse(json).getObject();

        assertNotNull(searchedFoodItems);
        final var firstItem = SearchFoodItemDigestMapper.map(searchedFoodItems.getBranded()[0]);

        /*
              {
                "food_name": "Cheese, Grilled Cheese Inspired Blend",
                "serving_unit": "bars",
                "nix_brand_id": "51db37ec176fe9790a89a84c",
                "brand_name_item_name": "Just the Cheese Cheese, Grilled Cheese Inspired Blend",
                "serving_qty": 2,
                "nf_calories": 120,
                "photo": {
                  "thumb": "https://assets.syndigo.com/dfe0e8d1-9a82-4d40-9e57-20d9f2bb8128"
                },
                "brand_name": "Just the Cheese",
                "region": 1,
                "brand_type": 2,
                "nix_item_id": "64818be2239bce0008ba8b8b",
                "locale": "en_US"
              },
         */
        assertEquals("Cheese, Grilled Cheese Inspired Blend", firstItem.getFoodName());
        assertEquals("bars", firstItem.getServingUnit());
        assertEquals("51db37ec176fe9790a89a84c", firstItem.getNixBrandId());
        assertEquals("Just the Cheese Cheese, Grilled Cheese Inspired Blend", firstItem.getBrandNameItemName());
        assertEquals(2, firstItem.getServingQty());
        assertEquals(120, firstItem.getCalories());
        assertEquals("https://assets.syndigo.com/dfe0e8d1-9a82-4d40-9e57-20d9f2bb8128", firstItem.getPhoto().getThumb());
        assertEquals("Just the Cheese", firstItem.getBrandName());
        assertEquals("64818be2239bce0008ba8b8b", firstItem.getNixItemId());
    }
}