package com.wildfit.server.service;

import java.util.List;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.service.handler.CreateMealHandler;
import com.wildfit.server.service.handler.DeleteMealHandler;
import com.wildfit.server.service.handler.RetrieveAllMealsHandler;
import com.wildfit.server.service.handler.RetrieveMealHandler;
import com.wildfit.server.service.handler.UpdateMealHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, a lightweight service, separation of concerns, and allows several people to work on
 * the same service without merge collisions.
 */
@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final RecipeRepository recipeRepository;
    private final MealRepository mealRepository;

    @Override
    public MealDigest retrieveMeal(Long mealId, String userId) throws WildfitServiceException {
        return RetrieveMealHandler.builder()
                                  .withMealRepository(mealRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withMealId(mealId)
                                  .withUserId(userId)
                                  .build().execute();
    }

    @Override
    public List<MealDigest> retrieveAllMeals(String userId) throws WildfitServiceException {
        return RetrieveAllMealsHandler.builder()
                                      .withMealRepository(mealRepository)
                                      .withRecipeRepository(recipeRepository)
                                      .withUserId(userId)
                                      .build().execute();
    }

    @Override
    public void deleteMeal(Long mealId, String userId) throws WildfitServiceException {
        DeleteMealHandler.builder()
                         .withMealRepository(mealRepository)
                         .withMealId(mealId)
                         .withUserId(userId)
                         .build().execute();
    }

    @Override
    public MealDigest createMeal(CreateMealRequest request) throws WildfitServiceException {
        return CreateMealHandler.builder()
                                .withMealRepository(mealRepository)
                                .withRecipeRepository(recipeRepository)
                                .withRequest(request)
                                .build().execute();
    }

    @Override
    public MealDigest updateMeal(MealDigest request) throws WildfitServiceException {
        return UpdateMealHandler.builder()
                                .withMealRepository(mealRepository)
                                .withRecipeRepository(recipeRepository)
                                .withRequest(request)
                                .build().execute();
    }
}
