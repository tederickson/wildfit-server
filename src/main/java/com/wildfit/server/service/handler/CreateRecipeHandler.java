package com.wildfit.server.service.handler;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class CreateRecipeHandler extends CommonRecipeHandler {
    private final RecipeDigest request;

    public RecipeDigest execute() throws WildfitServiceException {
        validate();

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new WildfitServiceException(
                                               WildfitServiceError.USER_NOT_FOUND));

        final var recipe = RecipeMapper.create(request, user.getEmail());
        final var saved = recipeRepository.save(recipe);
        return RecipeMapper.map(saved);
    }

    protected void validate() throws WildfitServiceException {
        super.validate();

        if (request == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
