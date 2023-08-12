package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.Recipe1Repository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(setterPrefix = "with")
public class GetRecipeHandler {
    private final Recipe1Repository recipe1Repository;
    private final Long recipeId;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var recipe = recipe1Repository
                .findById(recipeId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        return RecipeMapper.map(recipe);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(recipe1Repository, "recipe1Repository");

        if (recipeId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
