package com.wildfit.server.service.handler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.domain.MealSummaryDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class CommonMealHandler {
    protected final MealRepository mealRepository;

    protected Map<Long, Recipe> getRecipeMap(RecipeRepository recipeRepository, List<Long> recipeIds) {
        final Map<Long, Recipe> recipeMap = new HashMap<>();

        for (var recipe : recipeRepository.findAllById(recipeIds)) {
            recipeMap.put(recipe.getId(), recipe);
        }
        return recipeMap;
    }

    protected void determineStartAndEndDates(MealDigest mealDigest) {
        final var planDates = mealDigest.getRecipes().stream()
                                        .map(MealSummaryDigest::getPlanDate)
                                        .collect(Collectors.toSet());
        if (planDates.isEmpty()) {
            mealDigest.setStartDate(LocalDate.now());
            mealDigest.setEndDate(mealDigest.getStartDate());
        } else {
            final var itr = planDates.iterator();
            mealDigest.setStartDate(itr.next());
            mealDigest.setEndDate(mealDigest.getStartDate());

            while (itr.hasNext()) {
                final var planDate = itr.next();

                if (mealDigest.getStartDate().isAfter(planDate)) {
                    mealDigest.setStartDate(planDate);
                }
                if (mealDigest.getEndDate().isBefore(planDate)) {
                    mealDigest.setEndDate(planDate);
                }
            }
        }
    }

    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(mealRepository, " mealRepository");
    }
}
