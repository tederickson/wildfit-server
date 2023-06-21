package com.wildfit.server.service.handler;

import java.util.Objects;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.mapper.InstructionGroupMapper;
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
                .withEmail(user.getEmail())
                .withIntroduction(request.getIntroduction())
                .withName(request.getName())
                .withSeason(request.getSeason().getCode())
                .withPrepTimeMin(request.getPrepTimeMin())
                .withCookTimeMin(request.getCookTimeMin())
                .withServingUnit(request.getServingUnit())
                .withServingQty(request.getServingQty())
                .withCreated(java.time.LocalDateTime.now())
                .build();

        final var dbRecipe = recipeRepository.save(recipe);
        if (request.getInstructionGroups() != null) {
            final var instructionGroups = request.getInstructionGroups().stream()
                    .map(instructionGroupDigest -> InstructionGroupMapper.create(dbRecipe, instructionGroupDigest))
                    .collect(Collectors.toSet());
            dbRecipe.setInstructionGroups(instructionGroups);
        }

        return RecipeMapper.map(recipeRepository.save(dbRecipe));
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
