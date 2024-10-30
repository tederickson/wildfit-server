package com.wildfit.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonRecipeTest {

    @Test
    void getRecipeGroup() {
        final RecipeGroup recipeGroup = new RecipeGroup().setId(123L);
        final Instruction instruction = new Instruction()
                .setId(3L)
                .setText("Bob")
                .setStepNumber(1);
        final CommonRecipe commonRecipe = instruction.setRecipeGroup(recipeGroup);

        assertEquals(recipeGroup, instruction.getRecipeGroup());
        assertEquals(CommonRecipeType.INSTRUCTION, instruction.getType());
        assertEquals(3L, instruction.getId());
        assertEquals(3L, commonRecipe.getId());
    }
}