package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.MealSummaryDigest;
import com.wildfit.server.model.MealSummary;
import com.wildfit.server.model.Recipe;

import java.util.Map;

public final class MealSummaryMapper {
    public static MealSummaryDigest map(MealSummary mealSummary, Map<Long, Recipe> recipeMap) {
        final var recipe = recipeMap.get(mealSummary.getRecipeId());
        final var builder = MealSummaryDigest.builder();

        if (recipe != null) {
            builder.withId(mealSummary.getId())
                    .withRecipeId(recipe.getId())
                    .withName(recipe.getName())
                    .withSeason(recipe.getSeason().toSeasonType())
                    .withPlanDate(mealSummary.getPlanDate())
                    .withCooked(mealSummary.isCooked())
                    .withThumbnail(recipe.getThumbnail());
        }
        return builder.build();
    }

    public static MealSummary create(Recipe recipe) {
        return new MealSummary().setRecipeId(recipe.getId());
    }

    public static MealSummary update(MealSummaryDigest summary, MealSummary summaryMapEntity) {
        summaryMapEntity
                .setCooked(summary.isCooked())
                .setPlanDate(summary.getPlanDate());

        return summaryMapEntity;
    }
}
