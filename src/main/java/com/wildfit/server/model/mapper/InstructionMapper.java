package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.model.CommonRecipe;

public final class InstructionMapper {
    private InstructionMapper() {
    }

    public static com.wildfit.server.model.Instruction createInstruction(InstructionDigest instruction) {
        return new com.wildfit.server.model.Instruction()
                .setId(instruction.id())
                .setStepNumber(instruction.stepNumber())
                .setText(instruction.instruction());
    }

    public static InstructionDigest createInstruction(com.wildfit.server.model.Instruction instruction) {
        return InstructionDigest.builder()
                .withId(instruction.getId())
                .withStepNumber(instruction.getStepNumber())
                .withInstruction(instruction.getText())
                .build();
    }

    public static CommonRecipe updateInstruction(com.wildfit.server.model.Instruction existingInstruction,
                                                 InstructionDigest instruction) {
        return existingInstruction
                .setStepNumber(instruction.stepNumber())
                .setText(instruction.instruction());
    }
}
