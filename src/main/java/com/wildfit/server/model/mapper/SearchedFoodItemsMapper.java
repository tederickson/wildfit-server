package com.wildfit.server.model.mapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.model.SearchedFoodItems;

public class SearchedFoodItemsMapper {
    private SearchedFoodItemsMapper() {
    }

    public static SearchFoodResponse map(SearchedFoodItems searchedFoodItems) {
        final var response = new SearchFoodResponse();

        if (searchedFoodItems != null) {
            response.setBranded(
                    Arrays.stream(searchedFoodItems.getBranded())
                            .map(SearchFoodItemDigestMapper::map)
                            .collect(Collectors.toList()));
            response.setCommon(
                    Arrays.stream(searchedFoodItems.getCommon())
                            .map(SearchFoodItemDigestMapper::map)
                            .collect(Collectors.toList()));
        }

        return response;
    }
}