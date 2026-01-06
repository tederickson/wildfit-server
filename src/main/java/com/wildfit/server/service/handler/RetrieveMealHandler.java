package com.wildfit.server.service.handler;

import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Meal;
import com.wildfit.server.model.MealSummary;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.mapper.MealMapper;
import com.wildfit.server.repository.RecipeRepository;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

@SuperBuilder(setterPrefix = "with")
public class RetrieveMealHandler extends CommonMealHandler {
    private final RecipeRepository recipeRepository;
    private final Long mealId;
    private final String userId;

    public MealDigest execute() throws WildfitServiceException {
        validate();

        final Meal meal = mealRepository
                .findByIdAndUuid(mealId, userId)
                .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.MEAL_NOT_FOUND));

        final var recipeIds = meal.getRecipes().stream()
                .map(MealSummary::getRecipeId)
                .toList();

        final Map<Long, Recipe> recipeMap = getRecipeMap(recipeRepository, recipeIds);

        return MealMapper.map(meal, recipeMap);
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        Objects.requireNonNull(recipeRepository, " recipeRepository");

        if (mealId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (StringUtils.isAllBlank(userId)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
