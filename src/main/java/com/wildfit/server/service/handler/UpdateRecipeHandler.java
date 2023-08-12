package com.wildfit.server.service.handler;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder(setterPrefix = "with")
public class UpdateRecipeHandler extends CommonRecipeHandler {
    private final RecipeDigest request;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var dbRecipe = getAuthorizedRecipe(request.getId());
        RecipeMapper.update(dbRecipe, request);

        recipe1Repository.save(dbRecipe);

        return null;
    }


    protected void validate() throws UserServiceException {
        super.validate();

        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
