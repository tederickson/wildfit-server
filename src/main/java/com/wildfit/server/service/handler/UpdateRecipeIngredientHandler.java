package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.UpdateIngredientRequest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeIngredientMapper;
import com.wildfit.server.repository.RecipeIngredientRepository;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class UpdateRecipeIngredientHandler extends CommonRecipeHandler {
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final Long recipeId;
    private final Long ingredientId;
    private final UpdateIngredientRequest request;

    public IngredientDigest execute() throws UserServiceException {
        validate();
        getAuthorizedRecipe(recipeId);

        final var ingredient = recipeIngredientRepository.findById(ingredientId)
                                                         .orElseThrow(() -> new UserServiceException(
                                                                 UserServiceError.INGREDIENT_NOT_FOUND));

        RecipeIngredientMapper.update(request, ingredient);

        return RecipeIngredientMapper.map(recipeIngredientRepository.save(ingredient));
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
        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
