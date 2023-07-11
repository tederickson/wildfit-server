package com.wildfit.server.service.handler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.mapper.InstructionGroupMapper;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.InstructionGroupRepository;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public class CreateRecipeHandler extends CommonRecipeHandler {
    private final InstructionGroupRepository instructionGroupRepository;
    private final RecipeDigest request;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findByUuid(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));

        final var recipe = RecipeMapper.create(request, user.getEmail());
        final var dbRecipe = recipeRepository.save(recipe);

        List<InstructionGroup> instructionGroups = null;
        if (request.getInstructionGroups() != null) {
            instructionGroups = request.getInstructionGroups().stream()
                    .map(instructionGroupDigest -> InstructionGroupMapper.create(dbRecipe, instructionGroupDigest))
                    .map(instructionGroupRepository::save)
                    .collect(Collectors.toList());
        }

        return RecipeMapper.map(dbRecipe, instructionGroups);
    }

    protected void validate() throws UserServiceException {
        super.validate();
        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");

        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
