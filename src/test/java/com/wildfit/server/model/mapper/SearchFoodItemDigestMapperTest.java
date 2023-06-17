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

    @Value("classpath:search-instant-spinach-branded.json")
    Resource spinachSearch;

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

        /* https://trackapi.nutritionix.com/v2/search/instant?query=grilled cheese&branded_type=2
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

    @Test
    void readResponseBrandedOnly() throws IOException {
        String json = Files.readString(spinachSearch.getFile().toPath());
        final var searchedFoodItems = jacksonTester.parse(json).getObject();

        assertNotNull(searchedFoodItems);
        assertNull(searchedFoodItems.getCommon());
        final var item = SearchFoodItemDigestMapper.map(searchedFoodItems.getBranded()[1]);
        /* https://trackapi.nutritionix.com/v2/search/instant?query=spinach&branded_type=2&branded=true&common=false&detailed=true
    {
      "food_name": "Baby Spinach, Organic",
      "nix_brand_id": "51db37c9176fe9790a8995fb",
      "photo": {
        "thumb": "https://nutritionix-api.s3.amazonaws.com/5d3d4a6b242f7f2c633e90e1.jpeg"
      },
      "brand_name": "Earthbound Farm",
      "full_nutrients": [
        {
          "value": 2,
          "attr_id": 203
        },
        {
          "value": 0,
          "attr_id": 204
        },
        {
          "value": 3,
          "attr_id": 205
        },
        {
          "value": 25,
          "attr_id": 208
        },
        {
          "value": 2,
          "attr_id": 291
        },
        {
          "value": 80,
          "attr_id": 301
        },
        {
          "value": 2.3,
          "attr_id": 303
        },
        {
          "value": 470,
          "attr_id": 306
        },
        {
          "value": 65,
          "attr_id": 307
        }
      ],
      "serving_weight_grams": 85,
      "nix_item_id": "53cd1c1d9628b8892a249eb1",
      "nf_metric_qty": 85,
      "serving_unit": "cups",
      "brand_name_item_name": "Earthbound Farm Baby Spinach, Organic",
      "serving_qty": 2,
      "nf_calories": 25,
      "region": 1,
      "brand_type": 2,
      "nf_metric_uom": "g",
      "locale": "en_US"
    },
         */
        assertEquals("Baby Spinach, Organic", item.getFoodName());
        assertEquals("cups", item.getServingUnit());
        assertEquals("51db37c9176fe9790a8995fb", item.getNixBrandId());
        assertEquals("Earthbound Farm Baby Spinach, Organic", item.getBrandNameItemName());
        assertEquals(2, item.getServingQty());
        assertEquals(25, item.getCalories());
        assertEquals("https://nutritionix-api.s3.amazonaws.com/5d3d4a6b242f7f2c633e90e1.jpeg", item.getPhoto().getThumb());
        assertEquals("Earthbound Farm", item.getBrandName());
        assertEquals("53cd1c1d9628b8892a249eb1", item.getNixItemId());
        assertEquals(25.0, item.getCalories(), 0.01);
        assertEquals(3.0, item.getTotalCarbohydrate(), 0.01);
    }
}