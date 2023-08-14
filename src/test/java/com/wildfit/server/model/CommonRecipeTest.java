package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommonRecipeTest {

    @Test
    void getRecipeGroup() {
        final var recipeGroup = new RecipeGroup().setId(123L);
        final var instruction = new Instruction()
                .setId(3L)
                .setText("Bob")
                .setStepNumber(1)
                .setRecipeGroup(recipeGroup);

        assertEquals(recipeGroup, instruction.getRecipeGroup());
        assertEquals(CommonRecipeType.INSTRUCTION, instruction.getType());
        assertEquals(3L, instruction.getId());
    }
}