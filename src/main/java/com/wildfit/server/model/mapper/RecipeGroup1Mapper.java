package com.wildfit.server.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.model.RecipeGroup1;

public class RecipeGroup1Mapper {
    private RecipeGroup1Mapper() {
    }

    public static RecipeGroup1 create(InstructionGroupDigest instructionGroup) {
        final var recipeGroup = new RecipeGroup1()
                .setId(instructionGroup.getId())
                .setName(instructionGroup.getName())
                .setRecipeGroupNumber(instructionGroup.getInstructionGroupNumber());
        if (instructionGroup.getInstructions() != null) {
            for (var instruction : instructionGroup.getInstructions()) {
                recipeGroup.add(InstructionMapper.createInstruction(instruction));
            }
        }
        if (instructionGroup.getIngredients() != null) {
            for (var ingredient : instructionGroup.getIngredients()) {
                recipeGroup.add(IngredientDigestMapper.createIngredient(ingredient));
            }
        }
        return recipeGroup;
    }

    public static InstructionGroupDigest map(RecipeGroup1 recipeGroup) {
        final var builder = InstructionGroupDigest.builder()
                                                  .withId(recipeGroup.getId())
                                                  .withName(recipeGroup.getName())
                                                  .withInstructionGroupNumber(recipeGroup.getRecipeGroupNumber());

        List<InstructionDigest> instructions = new ArrayList<>();
        List<IngredientDigest> ingredients = new ArrayList<>();

        for (var common : recipeGroup.getCommonRecipes()) {
            final var type = com.wildfit.server.model.CommonRecipeType.valueOf(common.getType());

            if (com.wildfit.server.model.CommonRecipeType.INSTRUCTION == type) {
                instructions.add(InstructionMapper.createInstruction((com.wildfit.server.model.Instruction1) common));
            } else {
                ingredients.add(IngredientDigestMapper.createIngredient((com.wildfit.server.model.Ingredient) common));
            }
        }

        return builder
                .withInstructions(instructions)
                .withIngredients(ingredients)
                .build();
    }
}
