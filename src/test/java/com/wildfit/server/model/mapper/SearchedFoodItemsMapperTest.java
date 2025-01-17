package com.wildfit.server.model.mapper;

import com.wildfit.server.model.SearchedFoodItems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JsonTest
class SearchedFoodItemsMapperTest {
    @Value("classpath:search-instant-grilled-cheese.json")
    Resource grilledCheeseSearch;

    @Autowired
    private JacksonTester<SearchedFoodItems> jacksonTester;

    @Test
    void nullParameters() {
        final var searchFoodItemDigest = SearchedFoodItemsMapper.map(null);
        assertNotNull(searchFoodItemDigest);
        assertTrue(searchFoodItemDigest.branded().isEmpty());
        assertTrue(searchFoodItemDigest.common().isEmpty());
    }

    @Test
    void readResponse() throws IOException {
        final var json = Files.readString(grilledCheeseSearch.getFile().toPath());
        final var searchedFoodItems = jacksonTester.parse(json).getObject();

        assertNotNull(searchedFoodItems);
        assertEquals(20, searchedFoodItems.getBranded().length);
        assertEquals(20, searchedFoodItems.getCommon().length);

        final var searchFoodItemDigest = SearchedFoodItemsMapper.map(searchedFoodItems);
        assertEquals(20, searchFoodItemDigest.branded().size());
        assertEquals(20, searchFoodItemDigest.common().size());

        /*
         *  "common": [
         *     {
         *       "food_name": "grilled cheese",
         *       "serving_unit": "sandwich",
         *       "tag_name": "grilled cheese",
         *       "serving_qty": 1,
         *       "common_type": null,
         *       "full_nutrients": [
         *         {
         *           "value": 11.736,
         *           "attr_id": 203
         *         }, ...
         *       "tag_id": "1763",
         *       "photo": {
         *         "thumb": "https://nix-tag-images.s3.amazonaws.com/1763_thumb.jpg"
         *       },
         *       "locale": "en_US"
         *     },
         */
        final var firstCommon = searchFoodItemDigest.common().getFirst();

        assertEquals("grilled cheese", firstCommon.foodName());
        assertEquals("sandwich", firstCommon.servingUnit());
        assertEquals(1, firstCommon.servingQty());
        assertEquals("https://nix-tag-images.s3.amazonaws.com/1763_thumb.jpg", firstCommon.photo().thumb());
        assertEquals(365.76, firstCommon.calories(), 0.01);
    }
}