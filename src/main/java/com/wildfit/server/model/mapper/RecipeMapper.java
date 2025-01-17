package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Season;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RecipeMapper {
    private RecipeMapper() {
    }

    public static RecipeDigest map(com.wildfit.server.model.Recipe recipe) {
        final var builder = RecipeDigest.builder()
                .withId(recipe.getId())
                .withIntroduction(recipe.getIntroduction())
                .withName(recipe.getName())
                .withSeason(recipe.getSeason().toSeasonType())
                .withPrepTimeMin(recipe.getPrepTimeMin())
                .withCookTimeMin(recipe.getCookTimeMin())
                .withServingUnit(recipe.getServingUnit())
                .withServingQty(recipe.getServingQty())
                .withThumbnail(recipe.getThumbnail());

        if (recipe.getRecipeGroups() != null) {
            builder.withRecipeGroups(recipe.getRecipeGroups().stream().map(RecipeGroupMapper::map)
                                             .collect(Collectors.toList()));
        }
        return builder.build();
    }

    public static com.wildfit.server.model.Recipe create(RecipeDigest request, String email)
            throws WildfitServiceException {
        final var season = Season.map(request.getSeason());

        final var recipe = new com.wildfit.server.model.Recipe()
                .setEmail(email)
                .setIntroduction(request.getIntroduction())
                .setName(request.getName())
                .setSeason(season)
                .setPrepTimeMin(request.getPrepTimeMin())
                .setCookTimeMin(request.getCookTimeMin())
                .setServingUnit(request.getServingUnit())
                .setServingQty(request.getServingQty())
                .setCreated(LocalDateTime.now())
                .setThumbnail(request.getThumbnail());

        if (request.getRecipeGroups() != null) {
            for (var instructionGroup : request.getRecipeGroups()) {
                recipe.add(RecipeGroupMapper.create(instructionGroup));
            }
        }

        recipe.assignAllParents();

        return recipe;
    }

    public static void update(com.wildfit.server.model.Recipe recipe, RecipeDigest request)
            throws WildfitServiceException {
        final var season = Season.map(request.getSeason());

        // Never change the email address
        recipe.setIntroduction(request.getIntroduction())
                .setName(request.getName())
                .setSeason(season)
                .setPrepTimeMin(request.getPrepTimeMin())
                .setCookTimeMin(request.getCookTimeMin())
                .setServingUnit(request.getServingUnit())
                .setServingQty(request.getServingQty())
                .setUpdated(LocalDateTime.now())
                .setThumbnail(recipe.getThumbnail());

        Map<Long, com.wildfit.server.model.RecipeGroup> recipeGroup1Map = new HashMap<>();
        if (recipe.getRecipeGroups() != null) {
            recipeGroup1Map = recipe.getRecipeGroups().stream().collect(Collectors.toMap(
                    com.wildfit.server.model.RecipeGroup::getId, x -> x));
        }
        recipe.setRecipeGroups(new ArrayList<>());

        if (request.getRecipeGroups() != null) {
            for (var instructionGroup : request.getRecipeGroups()) {
                final var id = instructionGroup.id();

                if (id == null) {
                    recipe.add(RecipeGroupMapper.create(instructionGroup));
                }
                else {
                    final var existing = recipeGroup1Map.get(id);
                    Objects.requireNonNull(existing, "recipe group with id " + id);

                    recipe.add(RecipeGroupMapper.update(existing, instructionGroup));
                }
            }
        }

        recipe.assignAllParents();
    }
}
