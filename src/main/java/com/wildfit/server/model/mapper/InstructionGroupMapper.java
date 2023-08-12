package com.wildfit.server.model.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Instruction;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.Recipe1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstructionGroupMapper {
    private InstructionGroupMapper() {
    }

    public static InstructionGroupDigest map(InstructionGroup instructionGroup) {
        final var builder = InstructionGroupDigest.builder()
                                                  .withId(instructionGroup.getId())
                                                  .withInstructionGroupNumber(
                                                          instructionGroup.getInstructionGroupNumber())
                                                  .withName(instructionGroup.getName());

        if (instructionGroup.getInstructions() != null) {
            builder.withInstructions(
                    instructionGroup.getInstructions().stream().map(InstructionMapper::map)
                                    .collect(Collectors.toList()));
        }
        return builder.build();
    }

    public static InstructionGroup create(Recipe1 recipe, InstructionGroupDigest digest) {
        final var instructionGroup = InstructionGroup.builder()
                                                     .withInstructionGroupNumber(digest.getInstructionGroupNumber())
                                                     .withName(digest.getName())
                                                     .withRecipeId(recipe.getId()).build();

        if (digest.getInstructions() != null) {
            final var instructions = digest.getInstructions().stream()
                                           .map(instructionDigest -> InstructionMapper.create(instructionGroup,
                                                   instructionDigest))
                                           .collect(Collectors.toList());
            instructionGroup.setInstructions(instructions);
        }
        return instructionGroup;
    }

    public static InstructionGroup update(InstructionGroup instructionGroup,
                                          InstructionGroupDigest instructionGroupDigest)
            throws UserServiceException {
        instructionGroup.setInstructionGroupNumber(instructionGroupDigest.getInstructionGroupNumber());
        instructionGroup.setName(instructionGroupDigest.getName());

        if (instructionGroupDigest.getInstructions() == null) {
            instructionGroup.setInstructions(null);
        } else {
            List<Instruction> instructions = new ArrayList<>();

            for (var instructionDigest : instructionGroupDigest.getInstructions()) {
                if (instructionDigest.getId() == null) {
                    instructions.add(InstructionMapper.create(instructionGroup, instructionDigest));
                } else {
                    final var instruction = instructionGroup.getInstructions().stream()
                                                            .filter(x -> instructionDigest.getId().equals(x.getId()))
                                                            .findFirst()
                                                            .orElse(null);
                    if (instruction == null) {
                        log.error("Unable to find id " + instructionDigest.getId());
                        throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
                    }
                    instructions.add(InstructionMapper.update(instruction, instructionDigest));
                }
            }
            instructionGroup.setInstructions(instructions);
        }

        return instructionGroup;
    }
}
