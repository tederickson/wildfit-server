package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class DeleteRecipeHandler extends CommonRecipeHandler {

    private final String season;
    private final Long recipeId;

    public void execute() throws WildfitServiceException {
        validate();

        final var recipe = getAuthorizedRecipe(recipeId);

        recipeRepository.delete(recipe);
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        if (recipeId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
