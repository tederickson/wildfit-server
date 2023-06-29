package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.InstructionGroupRepository;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class DeleteRecipeHandler extends AbstractRecipeHandler {
    private final InstructionGroupRepository instructionGroupRepository;

    private final String season;
    private final Long recipeId;

    public void execute() throws UserServiceException {
        validate();

        final var recipe = getAuthorizedRecipe(recipeId);
        final var instructionGroups = instructionGroupRepository.findByRecipeId(recipeId);
        instructionGroupRepository.deleteAll(instructionGroups);
        recipeRepository.delete(recipe);
    }

    protected void validate() throws UserServiceException {
        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");

        if (recipeId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
