package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.model.Instruction;
import com.wildfit.server.model.InstructionGroup;

public class InstructionMapper {
    private InstructionMapper() {
    }

    public static InstructionDigest map(Instruction instruction) {
        return InstructionDigest.builder()
                                .withId(instruction.getId())
                                .withStepNumber(instruction.getStepNumber())
                                .withInstruction(instruction.getText()).build();
    }

    public static Instruction create(InstructionGroup instructionGroup, InstructionDigest instructionDigest) {
        return Instruction.builder()
                          .withInstructionGroup(instructionGroup)
                          .withStepNumber(instructionDigest.getStepNumber())
                          .withText(instructionDigest.getInstruction())
                          .build();
    }

    public static Instruction update(Instruction instruction, InstructionDigest instructionDigest) {
        instruction.setStepNumber(instructionDigest.getStepNumber());
        instruction.setText(instructionDigest.getInstruction());

        return instruction;
    }
}
