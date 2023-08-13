package com.wildfit.server.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.model.CommonRecipe;
import com.wildfit.server.model.CommonRecipeType;
import com.wildfit.server.model.Ingredient;
import com.wildfit.server.model.Instruction1;
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

            if (CommonRecipeType.INSTRUCTION == type) {
                instructions.add(InstructionMapper.createInstruction((Instruction1) common));
            } else {
                ingredients.add(IngredientDigestMapper.createIngredient((Ingredient) common));
            }
        }

        return builder
                .withInstructions(instructions)
                .withIngredients(ingredients)
                .build();
    }

    public static RecipeGroup1 update(RecipeGroup1 existing, InstructionGroupDigest instructionGroup) {
        Map<Long, CommonRecipe> commonRecipeMap = new HashMap<>();
        if (existing.getCommonRecipes() != null) {
            commonRecipeMap = existing.getCommonRecipes().stream().collect(Collectors.toMap(
                    CommonRecipe::getId, x -> x));
        }
        existing.setCommonRecipes(new ArrayList<>());

        if (instructionGroup.getInstructions() != null) {
            for (var instruction : instructionGroup.getInstructions()) {
                final var id = instruction.getId();

                if (id == null) {
                    existing.add(InstructionMapper.createInstruction(instruction));
                } else {
                    final var existingInstruction = commonRecipeMap.get(id);
                    Objects.requireNonNull(existingInstruction, "instruction with id " + id);

                    existing.add(InstructionMapper.updateInstruction((Instruction1) existingInstruction, instruction));
                }
            }
        }
        if (instructionGroup.getIngredients() != null) {
            for (var ingredientDigest : instructionGroup.getIngredients()) {
                final var id = ingredientDigest.getId();

                if (id == null) {
                    existing.add(IngredientDigestMapper.createIngredient(ingredientDigest));
                } else {
                    final var existingIngredient = commonRecipeMap.get(id);
                    Objects.requireNonNull(existingIngredient, "ingredient with id " + id);

                    existing.add(
                            IngredientDigestMapper.updateIngredient((Ingredient) existingIngredient, ingredientDigest));
                }
            }
        }

        return existing;
    }
}
