package com.wildfit.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class RecipeGroupDigest {
    private Long id;
    private Integer instructionGroupNumber;
    private String name;
    private List<InstructionDigest> instructions;
    private List<IngredientDigest> ingredients;
}
