package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.InstructionDigest;

public class InstructionMapper {
    private InstructionMapper() {
    }

    public static com.wildfit.server.model.Instruction1 createInstruction(InstructionDigest instruction) {
        return new com.wildfit.server.model.Instruction1()
                .setId(instruction.getId())
                .setStepNumber(instruction.getStepNumber())
                .setText(instruction.getInstruction());
    }

    public static InstructionDigest createInstruction(com.wildfit.server.model.Instruction1 instruction) {
        return InstructionDigest.builder()
                                .withId(instruction.getId())
                                .withStepNumber(instruction.getStepNumber())
                                .withInstruction(instruction.getText())
                                .build();
    }
}
