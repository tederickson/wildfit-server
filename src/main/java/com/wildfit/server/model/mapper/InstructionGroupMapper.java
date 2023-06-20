package com.wildfit.server.model.mapper;

import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.Recipe;

public class InstructionGroupMapper {
    private InstructionGroupMapper() {
    }

    public static InstructionGroupDigest map(InstructionGroup instructionGroup) {
        final var builder = InstructionGroupDigest.builder()
                .withInstructionGroupNumber(instructionGroup.getInstructionGroupNumber())
                .withName(instructionGroup.getName());

        if (instructionGroup.getInstructions() != null) {
            builder.withInstructions(
                    instructionGroup.getInstructions().stream().map(InstructionMapper::map)
                            .collect(Collectors.toList()));
        }
        return builder.build();
    }

    public static InstructionGroup create(Recipe recipe, InstructionGroupDigest digest) {
        final var instructionGroup = InstructionGroup.builder()
                .withInstructionGroupNumber(digest.getInstructionGroupNumber())
                .withName(digest.getName())
                .withRecipe(recipe).build();

        if (digest.getInstructions() != null) {
            final var instructions = digest.getInstructions().stream()
                    .map(instructionDigest -> InstructionMapper.create(instructionGroup, instructionDigest))
                    .collect(Collectors.toList());
            instructionGroup.setInstructions(instructions
            );
        }
        return instructionGroup;
    }
}
