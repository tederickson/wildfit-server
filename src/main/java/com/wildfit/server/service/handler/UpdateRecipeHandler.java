package com.wildfit.server.service.handler;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder(setterPrefix = "with")
public class UpdateRecipeHandler extends CommonRecipeHandler {
    private final RecipeDigest request;

    public RecipeDigest execute() throws WildfitServiceException {
        validate();

        final var dbRecipe = getAuthorizedRecipe(request.getId());
        RecipeMapper.update(dbRecipe, request);

        return RecipeMapper.map(recipeRepository.save(dbRecipe));
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
