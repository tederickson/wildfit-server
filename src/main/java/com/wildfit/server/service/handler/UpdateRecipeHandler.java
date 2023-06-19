package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class UpdateRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    private final RecipeDigest request;
    private final Long userId;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));
        final var recipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        if (recipe.getEmail().equals(user.getEmail())) {
            recipe.setIntroduction(request.getIntroduction());
            recipe.setName(request.getName());
            recipe.setSeason(request.getSeason().getCode());
            recipe.setPrepTimeMin(request.getPrepTimeMin());
            recipe.setCookTimeMin(request.getCookTimeMin());
            recipe.setServingUnit(request.getServingUnit());
            recipe.setServingQty(request.getServingQty());
            recipe.setInstructions(request.getInstructions());

            recipe.setUpdated(java.time.LocalDateTime.now());

            recipeRepository.save(recipe);
        } else {
            throw new UserServiceException(UserServiceError.NOT_AUTHORIZED);
        }

        return RecipeMapper.map(recipeRepository.save(recipe));
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(recipeRepository, "recipeRepository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
