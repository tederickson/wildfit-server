package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(setterPrefix = "with")
public class GetRecipeHandler {
    private final com.wildfit.server.repository.RecipeRepository recipeRepository;
    private final Long recipeId;

    public RecipeDigest execute() throws WildfitServiceException {
        validate();

        final var recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.RECIPE_NOT_FOUND));

        return RecipeMapper.map(recipe);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(recipeRepository, "recipe1Repository");

        if (recipeId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
