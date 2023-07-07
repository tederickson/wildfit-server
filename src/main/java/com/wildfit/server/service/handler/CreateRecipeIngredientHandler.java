package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.RecipeIngredientMapper;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.RecipeIngredientRepository;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@SuperBuilder(setterPrefix = "with")
public class CreateRecipeIngredientHandler extends CommonRecipeHandler {
    private final InstructionGroupRepository instructionGroupRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final Long recipeId;
    private final Long recipeGroupId;
    private final IngredientDigest request;

    public IngredientDigest execute() throws UserServiceException {
        validate();

        // Validate recipeId and recipeGroupId
        getAuthorizedRecipe(recipeId);
        instructionGroupRepository.findById(recipeGroupId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_GROUP_NOT_FOUND));

        final var recipeIngredient = RecipeIngredientMapper.create(request, recipeId, recipeGroupId);

        return RecipeIngredientMapper.map(recipeIngredientRepository.save(recipeIngredient));
    }

    protected void validate() throws UserServiceException {
        super.validate();
        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");
        Objects.requireNonNull(recipeIngredientRepository, "recipeIngredientRepository");

        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (StringUtils.trimToNull(request.getFoodName()) == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (recipeGroupId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (recipeId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
