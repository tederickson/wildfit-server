package com.wildfit.server.service;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.domain.UpdateIngredientRequest;
import com.wildfit.server.exception.UserServiceException;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    RecipeListDigest listBySeason(SeasonType season, Pageable pageable) throws UserServiceException;

    RecipeListDigest listBySeasonAndIngredient(SeasonType season, String ingredientName, Pageable pageable)
            throws UserServiceException;

    RecipeListDigest listBySeasonAndName(SeasonType season, String recipeName, Pageable pageable)
            throws UserServiceException;

    RecipeDigest retrieveRecipe(Long recipeId) throws UserServiceException;

    void deleteRecipe(Long recipeId, String userId) throws UserServiceException;

    RecipeDigest createRecipe(String userId, RecipeDigest request) throws UserServiceException;

    RecipeDigest updateRecipe(String userId, RecipeDigest request) throws UserServiceException;

    IngredientDigest createRecipeIngredient(String userId, Long recipeId, Long recipeGroupId, IngredientDigest request)
            throws UserServiceException;

    void deleteRecipeIngredient(String userId, Long recipeId, Long ingredientId) throws UserServiceException;

    IngredientDigest updateRecipeIngredient(String userId, Long recipeId, Long ingredientId,
                                            UpdateIngredientRequest request) throws UserServiceException;
}
