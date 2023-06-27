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
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class CreateRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final InstructionGroupRepository instructionGroupRepository;
    private final UserRepository userRepository;

    private final RecipeDigest request;
    private final Long userId;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findById(userId)
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

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(recipeRepository, "recipeRepository");
        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");

        if (userId == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
