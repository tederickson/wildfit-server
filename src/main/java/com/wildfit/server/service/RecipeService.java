package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.exception.UserServiceException;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    RecipeListDigest listBySeason(String season, Pageable pageable) throws UserServiceException;

    void deleteRecipe(Long id, Long userId)throws UserServiceException;
}
