package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Meal;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.mapper.MealMapper;
import com.wildfit.server.repository.RecipeRepository;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

@SuperBuilder(setterPrefix = "with")
public class CreateMealHandler extends CommonMealHandler {
    private final RecipeRepository recipeRepository;
    private final CreateMealRequest request;

    public MealDigest execute() throws WildfitServiceException {
        validate();

        final Map<Long, Recipe> recipeMap = getRecipeMap(recipeRepository, request.recipeIds());

        final Meal meal = MealMapper.create(request, recipeMap);
        final Meal persistedMeal = mealRepository.save(meal);

        return MealMapper.map(persistedMeal, recipeMap);
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        Objects.requireNonNull(recipeRepository, " recipeRepository");
        Objects.requireNonNull(request, " request");

        if (StringUtils.isAllBlank(request.uuid())) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (CollectionUtils.isEmpty(request.recipeIds())) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
