package com.wildfit.server.model.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.Recipe1;
import com.wildfit.server.model.Season;

public final class RecipeMapper {
    private RecipeMapper() {
    }

    public static RecipeDigest map(Recipe1 recipe) {
        final var builder = getBuilder(recipe);

        List<InstructionGroupDigest> instructionGroups = new ArrayList<>();

        if (recipe.getRecipeGroups() != null) {
            for (var recipeGroup : recipe.getRecipeGroups()) {
                instructionGroups.add(RecipeGroup1Mapper.map(recipeGroup));
            }
        }
        return builder.withInstructionGroups(instructionGroups).build();
    }

    private static RecipeDigest.RecipeDigestBuilder getBuilder(Recipe1 recipe) {
        return RecipeDigest.builder()
                           .withId(recipe.getId())
                           .withIntroduction(recipe.getIntroduction())
                           .withName(recipe.getName())
                           .withSeason(SeasonType.valueOf(recipe.getSeasonName()))
                           .withPrepTimeMin(recipe.getPrepTimeMin())
                           .withCookTimeMin(recipe.getCookTimeMin())
                           .withServingUnit(recipe.getServingUnit())
                           .withServingQty(recipe.getServingQty());
    }

    public static RecipeDigest map(Recipe1 recipe, Collection<InstructionGroup> instructionGroups) {
        final var builder = getBuilder(recipe);

        if (instructionGroups != null) {
            builder.withInstructionGroups(instructionGroups.stream()
                                                           .map(InstructionGroupMapper::map)
                                                           .collect(Collectors.toList()));
        }

        return builder.build();
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

        // TODO: finish update
    }


}
