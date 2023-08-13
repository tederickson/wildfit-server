package com.wildfit.server.model.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.model.Recipe1;
import com.wildfit.server.model.RecipeGroup1;
import com.wildfit.server.model.Season;

public final class RecipeMapper {
    private RecipeMapper() {
    }

    public static RecipeDigest map(Recipe1 recipe) {
        final var builder = RecipeDigest.builder()
                                        .withId(recipe.getId())
                                        .withIntroduction(recipe.getIntroduction())
                                        .withName(recipe.getName())
                                        .withSeason(SeasonType.valueOf(recipe.getSeasonName()))
                                        .withPrepTimeMin(recipe.getPrepTimeMin())
                                        .withCookTimeMin(recipe.getCookTimeMin())
                                        .withServingUnit(recipe.getServingUnit())
                                        .withServingQty(recipe.getServingQty());
        List<InstructionGroupDigest> instructionGroups = new ArrayList<>();

        if (recipe.getRecipeGroups() != null) {
            for (var recipeGroup : recipe.getRecipeGroups()) {
                instructionGroups.add(RecipeGroup1Mapper.map(recipeGroup));
            }
        }
        return builder.withInstructionGroups(instructionGroups).build();
    }

    public static Recipe1 create(RecipeDigest request, String email) {
        final var season = Season.map(request.getSeason());

        final var recipe = new Recipe1()
                .setEmail(email)
                .setIntroduction(request.getIntroduction())
                .setName(request.getName())
                .setSeasonName(season)
                .setPrepTimeMin(request.getPrepTimeMin())
                .setCookTimeMin(request.getCookTimeMin())
                .setServingUnit(request.getServingUnit())
                .setServingQty(request.getServingQty())
                .setCreated(LocalDateTime.now());

        if (request.getInstructionGroups() != null) {
            for (var instructionGroup : request.getInstructionGroups()) {
                recipe.add(RecipeGroup1Mapper.create(instructionGroup));
            }
        }

        recipe.assignAllParents();

        return recipe;
    }

    public static void update(Recipe1 recipe, RecipeDigest request) {
        final var season = Season.map(request.getSeason());

        // Never change the email address
        recipe.setIntroduction(request.getIntroduction())
              .setName(request.getName())
              .setSeasonName(season)
              .setPrepTimeMin(request.getPrepTimeMin())
              .setCookTimeMin(request.getCookTimeMin())
              .setServingUnit(request.getServingUnit())
              .setServingQty(request.getServingQty())
              .setUpdated(LocalDateTime.now());

        Map<Long, RecipeGroup1> recipeGroup1Map = new HashMap<>();
        if (recipe.getRecipeGroups() != null) {
            recipeGroup1Map = recipe.getRecipeGroups().stream().collect(Collectors.toMap(
                    RecipeGroup1::getId, x -> x));
        }
        recipe.setRecipeGroups(new ArrayList<>());

        if (request.getInstructionGroups() != null) {
            for (var instructionGroup : request.getInstructionGroups()) {
                final var id = instructionGroup.getId();

                if (id == null) {
                    recipe.add(RecipeGroup1Mapper.create(instructionGroup));
                } else {
                    final var existing = recipeGroup1Map.get(id);
                    Objects.requireNonNull(existing, "recipe group with id " + id);

                    recipe.add(RecipeGroup1Mapper.update(existing, instructionGroup));
                }
            }
        }

        recipe.assignAllParents();
    }


}
