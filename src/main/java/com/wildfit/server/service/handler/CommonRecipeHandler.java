package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class CommonRecipeHandler {
    protected final RecipeRepository recipeRepository;
    protected final UserRepository userRepository;

    protected final String userId;

    protected Recipe getAuthorizedRecipe(Long recipeId) throws WildfitServiceException {
        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new WildfitServiceException(
                                               WildfitServiceError.USER_NOT_FOUND));
        final var dbRecipe = recipeRepository.findById(recipeId)
                                             .orElseThrow(
                                                     () -> new WildfitServiceException(
                                                             WildfitServiceError.RECIPE_NOT_FOUND));

        if (!dbRecipe.getEmail().equals(user.getEmail())) {
            throw new WildfitServiceException(WildfitServiceError.NOT_AUTHORIZED);
        }
        return dbRecipe;
    }

    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(recipeRepository, " recipeRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
