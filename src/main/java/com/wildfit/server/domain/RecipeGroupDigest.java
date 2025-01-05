package com.wildfit.server.domain;

import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with")

public record RecipeGroupDigest(
        Long id,
        Integer instructionGroupNumber,
        String name,
        List<InstructionDigest> instructions,
        List<IngredientDigest> ingredients) {
}
