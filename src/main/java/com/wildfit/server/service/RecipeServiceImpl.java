package com.wildfit.server.service;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.InstructionRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateRecipeHandler;
import com.wildfit.server.service.handler.DeleteRecipeHandler;
import com.wildfit.server.service.handler.GetRecipeHandler;
import com.wildfit.server.service.handler.ListBySeasonHandler;
import com.wildfit.server.service.handler.UpdateRecipeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private InstructionGroupRepository instructionGroupRepository;
    @Autowired
    private InstructionRepository instructionRepository;

    @Override
    public RecipeListDigest listBySeason(SeasonType season, Pageable pageable) throws UserServiceException {
        return ListBySeasonHandler.builder()
                .withRecipeRepository(recipeRepository)
                .withSeason(season)
                .withPageable(pageable)
                .build().execute();
    }

    @Override
    public RecipeDigest retrieveRecipe(Long id) throws UserServiceException {
        return GetRecipeHandler.builder()
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeId(id)
                .build().execute();
    }

    @Override
    public void deleteRecipe(Long id, Long userId) throws UserServiceException {
        DeleteRecipeHandler.builder().withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withUserId(userId)
                .withRecipeId(id)
                .build().execute();
    }

    @Override
    public RecipeDigest createRecipe(Long userId, RecipeDigest request) throws UserServiceException {
        return CreateRecipeHandler.builder().withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withUserId(userId)
                .withRequest(request)
                .build().execute();
    }

    @Override
    public RecipeDigest updateRecipe(Long userId, RecipeDigest request) throws UserServiceException {
        return UpdateRecipeHandler.builder().withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withUserId(userId)
                .withRequest(request)
                .build().execute();
    }
}
