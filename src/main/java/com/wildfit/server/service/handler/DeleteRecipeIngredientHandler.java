package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.RecipeIngredientRepository;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder(setterPrefix = "with")
public class DeleteRecipeIngredientHandler extends AbstractRecipeHandler {
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final Long recipeId;
    private final Long ingredientId;

    public void execute() throws UserServiceException {
        validate();

        getAuthorizedRecipe(recipeId);
        recipeIngredientRepository.deleteById(ingredientId);
    }

    protected void validate() throws UserServiceException {
        super.validate();
        Objects.requireNonNull(recipeIngredientRepository, "recipeIngredientRepository");

        if (ingredientId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (recipeId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
