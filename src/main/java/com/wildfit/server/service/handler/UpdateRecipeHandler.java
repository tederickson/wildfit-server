package com.wildfit.server.service.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionGroupDigest;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder(setterPrefix = "with")
public class UpdateRecipeHandler {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final InstructionGroupRepository instructionGroupRepository;

    private final RecipeDigest request;
    private final Long userId;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(UserServiceError.USER_NOT_FOUND));
        final var dbRecipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        if (!dbRecipe.getEmail().equals(user.getEmail())) {
            throw new UserServiceException(UserServiceError.NOT_AUTHORIZED);
        }
        RecipeMapper.update(dbRecipe, request);

        recipeRepository.save(dbRecipe);

        final var dbInstructionGroupMap = instructionGroupRepository.findByRecipeId(request.getId()).stream()
                .collect(Collectors.toMap(InstructionGroup::getId, Function.identity()));

        final var instructionGroupDigestMap = new HashMap<Long, InstructionGroupDigest>();

        if (request.getInstructionGroups() != null) {
            for (var instructionGroupDigest : request.getInstructionGroups()) {
                if (instructionGroupDigest.getId() == null) {
                    instructionGroupRepository.save(InstructionGroupMapper.create(dbRecipe, instructionGroupDigest));
                } else {
                    instructionGroupDigestMap.put(instructionGroupDigest.getId(), instructionGroupDigest);
                }
            }
        }
        final var deleteRows = new HashSet<>(dbInstructionGroupMap.keySet());
        deleteRows.removeAll(instructionGroupDigestMap.keySet());

        if (!deleteRows.isEmpty()) {
            instructionGroupRepository.deleteAllById(deleteRows);
        }
        for (var instructionGroupDigest : instructionGroupDigestMap.values()) {
            final var dbInstructionGroup = dbInstructionGroupMap.get(instructionGroupDigest.getId());
            instructionGroupRepository.save(InstructionGroupMapper.update(dbInstructionGroup, instructionGroupDigest));
        }

        final var updatedRecipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        return RecipeMapper.map(updatedRecipe, instructionGroupRepository.findByRecipeId(request.getId()));
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
