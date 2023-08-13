package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.repository.Recipe1Repository;
import com.wildfit.server.repository.UserRepository;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class CommonRecipeHandler {
    protected final Recipe1Repository recipe1Repository;
    protected final UserRepository userRepository;

    protected final String userId;

    protected com.wildfit.server.model.Recipe getAuthorizedRecipe(Long recipeId) throws UserServiceException {
        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));
        final var dbRecipe = recipe1Repository.findById(recipeId)
                                             .orElseThrow(
                                                     () -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        if (!dbRecipe.getEmail().equals(user.getEmail())) {
            throw new UserServiceException(UserServiceError.NOT_AUTHORIZED);
        }
        return dbRecipe;
    }

    protected void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(recipe1Repository, "recipe1Repository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
