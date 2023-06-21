package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    RecipeListDigest listBySeason(SeasonType season, Pageable pageable) throws UserServiceException;

    RecipeDigest
    retrieveRecipe(Long id) throws UserServiceException;

    void deleteRecipe(Long id, Long userId) throws UserServiceException;

    RecipeDigest createRecipe(Long userId, RecipeDigest request) throws UserServiceException;

    RecipeDigest updateRecipe(Long userId, RecipeDigest request) throws UserServiceException;
}
