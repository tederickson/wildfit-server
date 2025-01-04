package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.model.SearchedFoodItems;

import java.util.Arrays;
import java.util.List;

public final class SearchedFoodItemsMapper {
    private SearchedFoodItemsMapper() {
    }

    public static SearchFoodResponse map(SearchedFoodItems searchedFoodItems) {
        if (searchedFoodItems == null) {
            return new SearchFoodResponse(List.of(), List.of());
        }
        var branded = Arrays.stream(searchedFoodItems.getBranded())
                .map(FoodItemDigestMapper::mapSearchedFoodItem).toList();
        var common = Arrays.stream(searchedFoodItems.getCommon())
                .map(FoodItemDigestMapper::mapSearchedFoodItem).toList();


        return new SearchFoodResponse(common, branded);
    }
}
