package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.CommonRecipe;
import com.wildfit.server.model.CommonRecipeType;
import com.wildfit.server.model.Ingredient;
import com.wildfit.server.model.RecipeGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RecipeGroupMapper {
    private RecipeGroupMapper() {
    }

    public static RecipeGroup create(RecipeGroupDigest instructionGroup) throws WildfitServiceException {
        final var recipeGroup = new RecipeGroup()
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

    public static RecipeGroupDigest map(RecipeGroup recipeGroup) {
        final var builder = RecipeGroupDigest.builder()
                .withId(recipeGroup.getId())
                .withName(recipeGroup.getName())
                .withInstructionGroupNumber(
                        recipeGroup.getRecipeGroupNumber());

        List<InstructionDigest> instructions = new ArrayList<>();
        List<IngredientDigest> ingredients = new ArrayList<>();

        for (var common : recipeGroup.getCommonRecipes()) {
            if (CommonRecipeType.INSTRUCTION == common.getType()) {
                instructions.add(InstructionMapper.createInstruction((com.wildfit.server.model.Instruction) common));
            }
            else {
                ingredients.add(IngredientDigestMapper.createIngredient((Ingredient) common));
            }
        }

        return builder
                .withInstructions(instructions)
                .withIngredients(ingredients)
                .build();
    }

    public static RecipeGroup update(RecipeGroup existing,
                                     RecipeGroupDigest instructionGroup) throws WildfitServiceException {
        Map<Long, CommonRecipe> commonRecipeMap = new HashMap<>();
        if (existing.getCommonRecipes() != null) {
            commonRecipeMap = existing.getCommonRecipes().stream().collect(Collectors.toMap(
                    CommonRecipe::getId, x -> x));
        }
        existing.setCommonRecipes(new ArrayList<>());

        if (instructionGroup.getInstructions() != null) {
            for (var instruction : instructionGroup.getInstructions()) {
                final var id = instruction.id();

                if (id == null) {
                    existing.add(InstructionMapper.createInstruction(instruction));
                }
                else {
                    final var existingInstruction = commonRecipeMap.get(id);
                    Objects.requireNonNull(existingInstruction, "instruction with id " + id);

                    existing.add(InstructionMapper.updateInstruction(
                            (com.wildfit.server.model.Instruction) existingInstruction, instruction));
                }
            }
        }
        if (instructionGroup.getIngredients() != null) {
            for (var ingredientDigest : instructionGroup.getIngredients()) {
                final var id = ingredientDigest.id();

                if (id == null) {
                    existing.add(IngredientDigestMapper.createIngredient(ingredientDigest));
                }
                else {
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
