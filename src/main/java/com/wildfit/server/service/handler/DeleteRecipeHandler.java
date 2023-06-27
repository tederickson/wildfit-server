package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class DeleteRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final InstructionGroupRepository instructionGroupRepository;
    private final UserRepository userRepository;
    private final String season;
    private final Long recipeId;
    private final Long userId;

    public void execute() throws UserServiceException {
        validate();

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));
        final var recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        if (recipe.getEmail().equals(user.getEmail())) {
            final var instructionGroups = instructionGroupRepository.findByRecipeId(recipeId);
            instructionGroupRepository.deleteAll(instructionGroups);
            recipeRepository.delete(recipe);
        } else {
            throw new UserServiceException(UserServiceError.NOT_AUTHORIZED);
        }
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(recipeRepository, "recipeRepository");
        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");

        if (recipeId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (userId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
