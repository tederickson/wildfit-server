package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.RecipeSummaryDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.model.Recipe1;

public class RecipeSummaryMapper {
    private RecipeSummaryMapper() {
    }

    public static RecipeSummaryDigest map(Recipe1 recipe) {
        return RecipeSummaryDigest.builder()
                                  .withId(recipe.getId())
                                  .withIntroduction(recipe.getIntroduction())
                                  .withName(recipe.getName())
                                  .withSeason(SeasonType.valueOf(recipe.getSeasonName()))
                                  .withPrepTimeMin(recipe.getPrepTimeMin())
                                  .withCookTimeMin(recipe.getCookTimeMin())
                                  .withServingUnit(recipe.getServingUnit())
                                  .withServingQty(recipe.getServingQty()).build();
    }
}
