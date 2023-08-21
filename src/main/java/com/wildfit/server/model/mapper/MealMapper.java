package com.wildfit.server.model.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.model.Meal;
import com.wildfit.server.model.MealSummary;
import com.wildfit.server.model.Recipe;

public final class MealMapper {
    private MealMapper() {
    }

    public static MealDigest map(Meal meal, Map<Long, Recipe> recipeMap) {
        final var builder = MealDigest.builder()
                                      .withId(meal.getId())
                                      .withUuid(meal.getUuid())
                                      .withStartDate(meal.getStartDate())
                                      .withEndDate(meal.getEndDate());

        if (meal.getRecipes() != null) {
            builder.withRecipes(meal.getRecipes().stream()
                                    .map(x -> MealSummaryMapper.map(x, recipeMap))
                                    .collect(Collectors.toList()));
        }
        return builder.build();
    }

    public static Meal create(CreateMealRequest request, Map<Long, Recipe> recipeMap) {
        final Meal meal = new Meal()
                .setUuid(request.getUuid())
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now());

        final List<MealSummary> recipes =
                recipeMap.values().stream().map(MealSummaryMapper::create).collect(Collectors.toList());
        recipes.forEach(x -> x.setMeal(meal));

        meal.setRecipes(recipes);

        return meal;
    }
}
