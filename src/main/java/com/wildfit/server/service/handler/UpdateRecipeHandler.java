package com.wildfit.server.service.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Instruction;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.mapper.InstructionGroupMapper;
import com.wildfit.server.model.mapper.RecipeMapper;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.InstructionRepository;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder(setterPrefix = "with")
public class UpdateRecipeHandler extends AbstractRecipeHandler {
    private final InstructionGroupRepository instructionGroupRepository;
    private final InstructionRepository instructionRepository;

    private final RecipeDigest request;

    public RecipeDigest execute() throws UserServiceException {
        validate();

        final var dbRecipe = getAuthorizedRecipe(request.getId());
        RecipeMapper.update(dbRecipe, request);

        recipeRepository.save(dbRecipe);

        deleteInstructionGroups();
        deleteInstructions();

        final var dbInstructionGroupMap = instructionGroupRepository.findByRecipeId(request.getId()).stream()
                .collect(Collectors.toMap(InstructionGroup::getId, Function.identity()));

        if (request.getInstructionGroups() != null) {
            for (var instructionGroupDigest : request.getInstructionGroups()) {
                if (instructionGroupDigest.getId() == null) {
                    instructionGroupRepository.save(InstructionGroupMapper.create(dbRecipe, instructionGroupDigest));
                } else {
                    final var dbInstructionGroup = dbInstructionGroupMap.get(instructionGroupDigest.getId());
                    instructionGroupRepository.save(InstructionGroupMapper.update(dbInstructionGroup, instructionGroupDigest));
                }
            }
        }

        final var updatedRecipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new UserServiceException(UserServiceError.RECIPE_NOT_FOUND));

        return RecipeMapper.map(updatedRecipe, instructionGroupRepository.findByRecipeId(request.getId()));
    }

    private void deleteInstructionGroups() {
        final var dbInstructions = instructionGroupRepository.findByRecipeId(request.getId()).stream()
                .map(InstructionGroup::getId)
                .collect(Collectors.toSet());
        final var instructions = request.getInstructionGroups().stream()
                .map(InstructionGroupDigest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        final var deleteRows = new HashSet<>(dbInstructions);
        deleteRows.removeAll(instructions);

        if (!deleteRows.isEmpty()) {
            instructionGroupRepository.deleteAllById(deleteRows);
            instructionGroupRepository.flush();
        }
    }

    private void deleteInstructions() {
        final var dbInstructionMap = instructionGroupRepository.findByRecipeId(request.getId()).stream()
                .map(InstructionGroup::getInstructions)
                .flatMap(List::stream)
                .collect(Collectors.toMap(Instruction::getId, Function.identity()));
        request.getInstructionGroups().stream()
                .map(InstructionGroupDigest::getInstructions)
                .flatMap(List::stream)
                .map(InstructionDigest::getId)
                .filter(Objects::nonNull)
                .forEach(dbInstructionMap::remove);

        for (var entity : dbInstructionMap.values()) {
            final var parentEntity = entity.getInstructionGroup();
            parentEntity.deleteInstruction(entity);
            instructionGroupRepository.saveAndFlush(parentEntity);
        }
    }

    protected void validate() throws UserServiceException {
        super.validate();

        Objects.requireNonNull(instructionGroupRepository, "instructionGroupRepository");
        Objects.requireNonNull(instructionRepository, "instructionRepository");

        if (request == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
        if (request.getSeason() == null) {
            throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
        }
    }
}
