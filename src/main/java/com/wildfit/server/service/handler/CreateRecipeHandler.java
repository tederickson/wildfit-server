package com.wildfit.server.service.handler;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeMapper;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class CreateRecipeHandler extends CommonRecipeHandler {
    private final RecipeDigest request;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));

        final var recipe = RecipeMapper.create(request, user.getEmail());
        final var saved = recipe1Repository.save(recipe);
        return RecipeMapper.map(saved);
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
