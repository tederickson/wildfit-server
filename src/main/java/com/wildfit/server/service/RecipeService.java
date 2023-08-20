package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceException;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    RecipeListDigest listBySeason(SeasonType season, Pageable pageable) throws WildfitServiceException;

    RecipeListDigest listBySeasonAndIngredient(SeasonType season, String ingredientName, Pageable pageable)
            throws WildfitServiceException;

    RecipeListDigest listBySeasonAndName(SeasonType season, String recipeName, Pageable pageable)
            throws WildfitServiceException;

    RecipeDigest retrieveRecipe(Long recipeId) throws WildfitServiceException;

    void deleteRecipe(Long recipeId, String userId) throws WildfitServiceException;

    RecipeDigest createRecipe(String userId, RecipeDigest request) throws WildfitServiceException;

    RecipeDigest updateRecipe(String userId, RecipeDigest request) throws WildfitServiceException;

}
