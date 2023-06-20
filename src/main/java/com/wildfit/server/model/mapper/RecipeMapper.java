package com.wildfit.server.model.mapper;

import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.model.Recipe;

public final class RecipeMapper {
    private RecipeMapper() {
    }

    public static RecipeDigest map(Recipe recipe) {
        final var builder = RecipeDigest.builder()
                .withId(recipe.getId())
                .withIntroduction(recipe.getIntroduction())
                .withName(recipe.getName())
                .withSeason(SeasonType.findByCode(recipe.getSeason()))
                .withPrepTimeMin(recipe.getPrepTimeMin())
                .withCookTimeMin(recipe.getCookTimeMin())
                .withServingUnit(recipe.getServingUnit())
                .withServingQty(recipe.getServingQty());

        if (recipe.getInstructionGroups() != null) {
            builder.withInstructionGroups(recipe.getInstructionGroups().stream()
                    .map(InstructionGroupMapper::map).collect(Collectors.toList()));
        }

        return builder.build();
    }


}
