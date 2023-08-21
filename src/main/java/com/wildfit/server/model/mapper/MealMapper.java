package com.wildfit.server.model.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Meal;
import com.wildfit.server.model.MealSummary;
import com.wildfit.server.model.Recipe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        meal.setRecipes(recipeMap.values().stream().map(MealSummaryMapper::create).toList());
        meal.updateSummaryMeals();

        return meal;
    }

    public static void update(MealDigest mealDigest, Meal entity, Map<Long, Recipe> recipeMap)
            throws WildfitServiceException {
        entity.setStartDate(mealDigest.getStartDate())
              .setEndDate(mealDigest.getEndDate());

        final Map<Long, MealSummary> summaryMap =
                entity.getRecipes().stream().collect(Collectors.toMap(MealSummary::getRecipeId, x -> x));
        final List<MealSummary> mealSummaries = new ArrayList<>();

        for (var summaryDigest : mealDigest.getRecipes()) {
            final var mapKey = summaryDigest.getRecipeId();
            final var recipe = recipeMap.get(mapKey);

            if (recipe != null) {
                if (summaryDigest.getId() == null) {
                    mealSummaries.add(MealSummaryMapper.create(recipe));
                } else {
                    final var summaryMapEntity = summaryMap.get(mapKey);

                    if (summaryMapEntity == null) {
                        log.error("summaryDigest {} not found", summaryDigest);
                        throw new WildfitServiceException(WildfitServiceError.MEAL_NOT_FOUND);
                    }
                    mealSummaries.add(MealSummaryMapper.update(summaryDigest, summaryMapEntity));
                }
            }
        }

        entity.setRecipes(mealSummaries);
        entity.updateSummaryMeals();
    }
}
