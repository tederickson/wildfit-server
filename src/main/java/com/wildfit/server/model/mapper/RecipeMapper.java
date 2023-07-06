package com.wildfit.server.model.mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.RecipeIngredient;
import com.wildfit.server.model.Season;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RecipeMapper {
    private RecipeMapper() {
    }

    public static RecipeDigest map(Recipe recipe) {
        final var builder = getBuilder(recipe);

        return builder.build();
    }

    private static RecipeDigest.RecipeDigestBuilder getBuilder(Recipe recipe) {
        final var season = Season.findByCode(recipe.getSeason());

        return RecipeDigest.builder()
                .withId(recipe.getId())
                .withIntroduction(recipe.getIntroduction())
                .withName(recipe.getName())
                .withSeason(season == null ? null : season.toSeasonType())
                .withPrepTimeMin(recipe.getPrepTimeMin())
                .withCookTimeMin(recipe.getCookTimeMin())
                .withServingUnit(recipe.getServingUnit())
                .withServingQty(recipe.getServingQty());
    }

    public static RecipeDigest map(Recipe recipe, Collection<InstructionGroup> instructionGroups) {
        final var builder = getBuilder(recipe);

        if (instructionGroups != null) {
            builder.withInstructionGroups(instructionGroups.stream()
                    .map(InstructionGroupMapper::map).collect(Collectors.toList()));
        }

        return builder.build();
    }

    public static RecipeDigest map(Recipe recipe,
                                   Collection<InstructionGroup> instructionGroups,
                                   Collection<RecipeIngredient> recipeIngredients) {
        final var builder = getBuilder(recipe);

        if (instructionGroups != null) {
            builder.withInstructionGroups(instructionGroups.stream()
                    .map(InstructionGroupMapper::map)
                    .collect(Collectors.toList()));
        }
        final var recipeDigest = builder.build();
        if (recipeIngredients != null) {
            // Attach the recipe ingredients to the appropriate recipe group
            for (var recipeGroup : recipeDigest.getInstructionGroups()) {
                final var recipeGroupId = recipeGroup.getId();
                final var ingredients = recipeIngredients.stream()
                        .filter(x -> x.getInstructionGroupId() == recipeGroupId)
                        .map(RecipeIngredientMapper::map)
                        .collect(Collectors.toList());
                recipeGroup.setIngredients(ingredients);
            }
        }

        return recipeDigest;
    }

    public static Recipe create(RecipeDigest request, String email) {
        final var season = Season.valueOf(request.getSeason().name());

        return Recipe.builder()
                .withEmail(email)
                .withIntroduction(request.getIntroduction())
                .withName(request.getName())
                .withSeason(season.getCode())
                .withPrepTimeMin(request.getPrepTimeMin())
                .withCookTimeMin(request.getCookTimeMin())
                .withServingUnit(request.getServingUnit())
                .withServingQty(request.getServingQty())
                .withCreated(LocalDateTime.now())
                .build();
    }

    public static void update(Recipe recipe, RecipeDigest request) {
        final var season = Season.valueOf(request.getSeason().name());

        // Never change the email address
        recipe.setIntroduction(request.getIntroduction());
        recipe.setName(request.getName());
        recipe.setSeason(season.getCode());
        recipe.setPrepTimeMin(request.getPrepTimeMin());
        recipe.setCookTimeMin(request.getCookTimeMin());
        recipe.setServingUnit(request.getServingUnit());
        recipe.setServingQty(request.getServingQty());
        recipe.setUpdated(LocalDateTime.now());
    }


}
