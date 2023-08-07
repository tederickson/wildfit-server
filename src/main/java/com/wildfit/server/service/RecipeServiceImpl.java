package com.wildfit.server.service;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.domain.UpdateIngredientRequest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.RecipeIngredientRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateRecipeHandler;
import com.wildfit.server.service.handler.CreateRecipeIngredientHandler;
import com.wildfit.server.service.handler.DeleteRecipeHandler;
import com.wildfit.server.service.handler.DeleteRecipeIngredientHandler;
import com.wildfit.server.service.handler.GetRecipeHandler;
import com.wildfit.server.service.handler.ListBySeasonAndIngredientHandler;
import com.wildfit.server.service.handler.ListBySeasonAndNameHandler;
import com.wildfit.server.service.handler.ListBySeasonHandler;
import com.wildfit.server.service.handler.UpdateRecipeHandler;
import com.wildfit.server.service.handler.UpdateRecipeIngredientHandler;
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
    private final RecipeRepository recipeRepository;
    private final InstructionGroupRepository instructionGroupRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public RecipeListDigest listBySeason(SeasonType season, Pageable pageable) throws UserServiceException {
        return ListBySeasonHandler.builder()
                                  .withRecipeRepository(recipeRepository)
                                  .withSeason(season)
                                  .withPageable(pageable)
                                  .build().execute();
    }

    @Override
    public RecipeListDigest listBySeasonAndIngredient(SeasonType season, String ingredientName, Pageable pageable)
            throws UserServiceException {
        return ListBySeasonAndIngredientHandler.builder()
                                               .withRecipeRepository(recipeRepository)
                                               .withRecipeIngredientRepository(recipeIngredientRepository)
                                               .withSeason(season)
                                               .withIngredientName(ingredientName)
                                               .withPageable(pageable)
                                               .build().execute();
    }

    @Override
    public RecipeListDigest listBySeasonAndName(SeasonType season, String recipeName, Pageable pageable)
            throws UserServiceException {
        return ListBySeasonAndNameHandler.builder()
                                         .withRecipeRepository(recipeRepository)
                                         .withRecipeIngredientRepository(recipeIngredientRepository)
                                         .withSeason(season)
                                         .withRecipeName(recipeName)
                                         .withPageable(pageable)
                                         .build().execute();
    }

    @Override
    public RecipeDigest retrieveRecipe(Long recipeId) throws UserServiceException {
        return GetRecipeHandler.builder()
                               .withRecipeRepository(recipeRepository)
                               .withInstructionGroupRepository(instructionGroupRepository)
                               .withRecipeIngredientRepository(recipeIngredientRepository)
                               .withRecipeId(recipeId)
                               .build().execute();
    }

    @Override
    public void deleteRecipe(Long recipeId, String userId) throws UserServiceException {
        DeleteRecipeHandler.builder()
                           .withUserRepository(userRepository)
                           .withRecipeRepository(recipeRepository)
                           .withInstructionGroupRepository(instructionGroupRepository)
                           .withUserId(userId)
                           .withRecipeId(recipeId)
                           .build().execute();
    }

    @Override
    public RecipeDigest createRecipe(String userId, RecipeDigest request) throws UserServiceException {
        return CreateRecipeHandler.builder()
                                  .withUserRepository(userRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withInstructionGroupRepository(instructionGroupRepository)
                                  .withRecipeIngredientRepository(recipeIngredientRepository)
                                  .withUserId(userId)
                                  .withRequest(request)
                                  .build().execute();
    }

    @Override
    public RecipeDigest updateRecipe(String userId, RecipeDigest request) throws UserServiceException {
        return UpdateRecipeHandler.builder()
                                  .withUserRepository(userRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withInstructionGroupRepository(instructionGroupRepository)
                                  .withUserId(userId)
                                  .withRequest(request)
                                  .build().execute();
    }

    @Override
    public IngredientDigest createRecipeIngredient(String userId, Long recipeId, Long recipeGroupId,
                                                   IngredientDigest request)
            throws UserServiceException {
        return CreateRecipeIngredientHandler.builder()
                                            .withUserRepository(userRepository)
                                            .withRecipeRepository(recipeRepository)
                                            .withInstructionGroupRepository(instructionGroupRepository)
                                            .withRecipeIngredientRepository(recipeIngredientRepository)
                                            .withUserId(userId)
                                            .withRequest(request)
                                            .withRecipeGroupId(recipeGroupId)
                                            .withRecipeId(recipeId)
                                            .build().execute();
    }

    @Override
    public void deleteRecipeIngredient(String userId, Long recipeId, Long ingredientId) throws UserServiceException {
        DeleteRecipeIngredientHandler.builder()
                                     .withUserRepository(userRepository)
                                     .withRecipeRepository(recipeRepository)
                                     .withRecipeIngredientRepository(recipeIngredientRepository)
                                     .withUserId(userId)
                                     .withRecipeId(recipeId)
                                     .withIngredientId(ingredientId)
                                     .build().execute();
    }

    @Override
    public IngredientDigest updateRecipeIngredient(String userId, Long recipeId, Long ingredientId,
                                                   UpdateIngredientRequest request) throws UserServiceException {
        return UpdateRecipeIngredientHandler.builder()
                                            .withUserRepository(userRepository)
                                            .withRecipeRepository(recipeRepository)
                                            .withRecipeIngredientRepository(recipeIngredientRepository)
                                            .withUserId(userId)
                                            .withRecipeId(recipeId)
                                            .withIngredientId(ingredientId)
                                            .build().execute();
    }
}
