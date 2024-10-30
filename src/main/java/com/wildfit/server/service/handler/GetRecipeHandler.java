package com.wildfit.server.service.handler;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.RecipeRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Builder(setterPrefix = "with")
public class GetRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final Long recipeId;

    public RecipeDigest execute() throws WildfitServiceException {
        validate();

        final var recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.RECIPE_NOT_FOUND));

        return RecipeMapper.map(recipe);
    }

    private void validate() throws WildfitServiceException {
        Objects.requireNonNull(recipeRepository, " recipeRepository");

        if (recipeId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
