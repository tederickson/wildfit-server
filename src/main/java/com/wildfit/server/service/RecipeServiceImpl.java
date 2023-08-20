package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateRecipeHandler;
import com.wildfit.server.service.handler.DeleteRecipeHandler;
import com.wildfit.server.service.handler.GetRecipeHandler;
import com.wildfit.server.service.handler.ListBySeasonAndIngredientHandler;
import com.wildfit.server.service.handler.ListBySeasonAndNameHandler;
import com.wildfit.server.service.handler.ListBySeasonHandler;
import com.wildfit.server.service.handler.UpdateRecipeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, a lightweight service, separation of concerns, and allows several people to work on
 * the same service without merge collisions.
 */
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final UserRepository userRepository;
    private final com.wildfit.server.repository.RecipeRepository recipeRepository;

    @Override
    public RecipeListDigest listBySeason(SeasonType season, Pageable pageable) throws WildfitServiceException {
        return ListBySeasonHandler.builder()
                                  .withRecipeRepository(recipeRepository)
                                  .withSeason(season)
                                  .withPageable(pageable)
                                  .build().execute();
    }

    @Override
    public RecipeListDigest listBySeasonAndIngredient(SeasonType season, String ingredientName, Pageable pageable)
            throws WildfitServiceException {
        return ListBySeasonAndIngredientHandler.builder()
                                               .withRecipeRepository(recipeRepository)
                                               .withSeason(season)
                                               .withIngredientName(ingredientName)
                                               .withPageable(pageable)
                                               .build().execute();
    }

    @Override
    public RecipeListDigest listBySeasonAndName(SeasonType season, String recipeName, Pageable pageable)
            throws WildfitServiceException {
        return ListBySeasonAndNameHandler.builder()
                                         .withRecipeRepository(recipeRepository)
                                         .withSeason(season)
                                         .withRecipeName(recipeName)
                                         .withPageable(pageable)
                                         .build().execute();
    }

    @Override
    public RecipeDigest retrieveRecipe(Long recipeId) throws WildfitServiceException {
        return GetRecipeHandler.builder()
                               .withRecipeRepository(recipeRepository)
                               .withRecipeId(recipeId)
                               .build().execute();
    }

    @Override
    public void deleteRecipe(Long recipeId, String userId) throws WildfitServiceException {
        DeleteRecipeHandler.builder()
                           .withUserRepository(userRepository)
                           .withRecipeRepository(recipeRepository)
                           .withUserId(userId)
                           .withRecipeId(recipeId)
                           .build().execute();
    }

    @Override
    public RecipeDigest createRecipe(String userId, RecipeDigest request) throws WildfitServiceException {
        return CreateRecipeHandler.builder()
                                  .withUserRepository(userRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withUserId(userId)
                                  .withRequest(request)
                                  .build().execute();
    }

    @Override
    public RecipeDigest updateRecipe(String userId, RecipeDigest request) throws WildfitServiceException {
        return UpdateRecipeHandler.builder()
                                  .withUserRepository(userRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withUserId(userId)
                                  .withRequest(request)
                                  .build().execute();
    }
}
