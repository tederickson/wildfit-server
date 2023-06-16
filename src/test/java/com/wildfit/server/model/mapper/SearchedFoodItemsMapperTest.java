package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.nio.file.Files;

import com.wildfit.server.domain.PhotoDigest;
import com.wildfit.server.model.SearchedFoodItems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

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
        assertNull(searchFoodItemDigest.getBranded());
        assertNull(searchFoodItemDigest.getCommon());
    }

    @Test
    void readResponse() throws IOException {
        final var json = Files.readString(grilledCheeseSearch.getFile().toPath());
        final var searchedFoodItems = jacksonTester.parse(json).getObject();

        assertNotNull(searchedFoodItems);
        assertEquals(20, searchedFoodItems.getBranded().length);
        assertEquals(20, searchedFoodItems.getCommon().length);

        final var searchFoodItemDigest = SearchedFoodItemsMapper.map(searchedFoodItems);
        assertEquals(20, searchFoodItemDigest.getBranded().size());
        assertEquals(20, searchFoodItemDigest.getCommon().size());

        /*
         *  "common": [
         *     {
         *       "food_name": "grilled cheese",
         *       "serving_unit": "sandwich",
         *       "tag_name": "grilled cheese",
         *       "serving_qty": 1,
         *       "common_type": null,
         *       "tag_id": "1763",
         *       "photo": {
         *         "thumb": "https://nix-tag-images.s3.amazonaws.com/1763_thumb.jpg"
         *       },
         *       "locale": "en_US"
         *     },
         */
        final var firstCommon = searchFoodItemDigest.getCommon().get(0);
        final var expected = com.wildfit.server.domain.SearchFoodItemDigest.builder()
                .withFoodName("grilled cheese")
                .withServingUnit("sandwich")
                .withServingQty(1)
                .withPhoto(PhotoDigest.builder()
                        .withThumb("https://nix-tag-images.s3.amazonaws.com/1763_thumb.jpg").build())
                .build();
        assertEquals(expected, firstCommon);
    }
}