package com.wildfit.server.model.mapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.wildfit.server.domain.*;
import com.wildfit.server.model.*;

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
