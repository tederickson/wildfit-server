package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(mealRepository, " mealRepository");
    }
}
