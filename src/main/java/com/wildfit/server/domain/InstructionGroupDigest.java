package com.wildfit.server.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class InstructionGroupDigest {
    private Long id;
    private Integer instructionGroupNumber;
    private String name;
    private List<InstructionDigest> instructions;
}
