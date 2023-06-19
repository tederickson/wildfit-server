package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class CreateRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    private final RecipeDigest request;
    private final Long userId;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));
        final var recipe = Recipe.builder()
                .withId(request.getId())
                .withEmail(user.getEmail())
                .withIntroduction(request.getIntroduction())
                .withName(request.getName())
                .withSeason(request.getSeason().getCode())
                .withPrepTimeMin(request.getPrepTimeMin())
                .withCookTimeMin(request.getCookTimeMin())
                .withServingUnit(request.getServingUnit())
                .withServingQty(request.getServingQty())
                .withInstructions(request.getInstructions())
                .withCreated(java.time.LocalDateTime.now())
                .build();

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
