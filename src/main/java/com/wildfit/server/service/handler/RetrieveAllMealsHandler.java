package com.wildfit.server.service.handler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
import org.springframework.util.CollectionUtils;

@SuperBuilder(setterPrefix = "with")
public class RetrieveAllMealsHandler extends CommonMealHandler {
    private final RecipeRepository recipeRepository;
    private final String userId;

    public List<MealDigest> execute() throws WildfitServiceException {
        validate();

        final List<Meal> meals = mealRepository.findAllByUuidOrderByStartDateDesc(userId);
        if (CollectionUtils.isEmpty(meals)) {
            throw new WildfitServiceException(WildfitServiceError.MEAL_NOT_FOUND);
        }
        final var recipeIds = meals.stream().map(Meal::getRecipes)
                                   .flatMap(List::stream)
                                   .map(MealSummary::getRecipeId)
                                   .collect(Collectors.toList());

        final Map<Long, Recipe> recipeMap = getRecipeMap(recipeRepository, recipeIds);

        return meals.stream().map(x -> MealMapper.map(x, recipeMap)).collect(Collectors.toList());
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        Objects.requireNonNull(recipeRepository, "recipeRepository");

        if (StringUtils.isAllBlank(userId)) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
