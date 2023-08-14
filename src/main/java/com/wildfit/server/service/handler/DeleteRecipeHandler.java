package com.wildfit.server.service.handler;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class DeleteRecipeHandler extends CommonRecipeHandler {

    private final String season;
    private final Long recipeId;

    public void execute() throws UserServiceException {
        validate();

        final var recipe = getAuthorizedRecipe(recipeId);

        recipeRepository.delete(recipe);
    }

    protected void validate() throws UserServiceException {
        super.validate();

        if (recipeId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
