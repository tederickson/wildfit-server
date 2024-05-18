package com.wildfit.server.service.handler;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.domain.MealSummaryDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Meal;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.mapper.MealMapper;
import com.wildfit.server.repository.RecipeRepository;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

@SuperBuilder(setterPrefix = "with")
public class UpdateMealHandler extends CommonMealHandler {
    private final RecipeRepository recipeRepository;
    private final MealDigest request;

    public MealDigest execute() throws WildfitServiceException {
        validate();

        final var entity = mealRepository.findById(request.getId())
                                         .orElseThrow(
                                                 () -> new WildfitServiceException(WildfitServiceError.MEAL_NOT_FOUND));

        final var recipeIds = request.getRecipes().stream().map(MealSummaryDigest::getRecipeId).toList();
        final Map<Long, Recipe> recipeMap = getRecipeMap(recipeRepository, recipeIds);

        determineStartAndEndDates();


        MealMapper.update(request, entity, recipeMap);
        final Meal persistedMeal = mealRepository.save(entity);

        return MealMapper.map(persistedMeal, recipeMap);
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        Objects.requireNonNull(recipeRepository, " recipeRepository");
        Objects.requireNonNull(request, " request");

        if (StringUtils.isAllBlank(request.getUuid())) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (CollectionUtils.isEmpty(request.getRecipes())) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }

    protected void determineStartAndEndDates() {
        final var planDates = request.getRecipes().stream()
                                     .map(MealSummaryDigest::getPlanDate)
                                     .filter(Objects::nonNull)
                                     .sorted()
                                     .toList();
        if (planDates.isEmpty()) {
            request.setStartDate(LocalDate.now());
            request.setEndDate(request.getStartDate());
        } else {
            request.setStartDate(planDates.getFirst());
            request.setEndDate(planDates.getLast());
        }
    }
}
