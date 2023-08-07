package com.wildfit.server.service.handler;

import java.util.Objects;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.InstructionGroupMapper;
import com.wildfit.server.model.mapper.RecipeIngredientMapper;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.RecipeIngredientRepository;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

@SuperBuilder(setterPrefix = "with")
public class CreateRecipeHandler extends CommonRecipeHandler {
    private final InstructionGroupRepository instructionGroupRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeDigest request;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findByUuid(userId)
                                       .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));

        final var recipe = RecipeMapper.create(request, user.getEmail());
        final var dbRecipe = recipeRepository.save(recipe);
        final var recipeId = dbRecipe.getId();

        if (request.getInstructionGroups() != null) {
            final var entities = request.getInstructionGroups().stream()
                                        .map(instructionGroupDigest -> InstructionGroupMapper.create(dbRecipe,
                                                instructionGroupDigest))
                                        .collect(Collectors.toList());

            // saveAll is faster than multiple saves because it is one transaction
            instructionGroupRepository.saveAll(entities);
        }
        final var instructionGroups = instructionGroupRepository.findByRecipeId(recipeId);

        for (var instructionGroup : instructionGroups) {
            final var recipeGroupId = instructionGroup.getId();
            final var requestInstructionGroup
                    = request.getInstructionGroups().stream()
                             .filter(x -> instructionGroup.getInstructionGroupNumber() == x.getInstructionGroupNumber())
                             .findFirst()
                             .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_GROUP_NOT_FOUND));
            if (!CollectionUtils.isEmpty(requestInstructionGroup.getIngredients())) {
                final var entities = requestInstructionGroup.getIngredients().stream()
                                                            .map(x -> RecipeIngredientMapper.create(x, recipeId,
                                                                    recipeGroupId))
                                                            .collect(Collectors.toList());
                recipeIngredientRepository.saveAll(entities);
            }
        }

        return RecipeMapper.map(recipe,
                instructionGroups,
                recipeIngredientRepository.findByRecipeId(recipeId));
    }

    protected void validate() throws UserServiceException {
        super.validate();
        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");
        Objects.requireNonNull(recipeIngredientRepository, "recipeIngredientRepository");

        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
