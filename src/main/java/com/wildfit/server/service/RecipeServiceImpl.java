package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.DeleteRecipeHandler;
import com.wildfit.server.service.handler.ListBySeasonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public RecipeListDigest listBySeason(String season, Pageable pageable) throws UserServiceException {
        return ListBySeasonHandler.builder()
                .withRecipeRepository(recipeRepository)
                .withSeason(season)
                .withPageable(pageable)
                .build().execute();
    }

    @Override
    public void deleteRecipe(Long id, Long userId) throws UserServiceException {
        DeleteRecipeHandler.builder().withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withUserId(userId)
                .withRecipeId(id)
                .build().execute();
    }
}
