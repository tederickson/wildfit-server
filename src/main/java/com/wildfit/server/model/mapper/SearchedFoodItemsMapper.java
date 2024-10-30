package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.model.SearchedFoodItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SearchedFoodItemsMapper {
    private SearchedFoodItemsMapper() {
    }

    public static SearchFoodResponse map(SearchedFoodItems searchedFoodItems) {
        final var response = new SearchFoodResponse();

        if (searchedFoodItems != null) {
            response.setBranded(
                    Arrays.stream(searchedFoodItems.getBranded())
                            .map(FoodItemDigestMapper::mapSearchedFoodItem)
                            .collect(Collectors.toList()));
            response.setCommon(
                    Arrays.stream(searchedFoodItems.getCommon())
                            .map(FoodItemDigestMapper::mapSearchedFoodItem)
                            .collect(Collectors.toList()));
        }

        return response;
    }
}
