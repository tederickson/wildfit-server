package com.wildfit.server.model.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.InstructionGroup;
import com.wildfit.server.model.Recipe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public static Recipe create(RecipeDigest request, String email) {
        final var recipe = Recipe.builder()
                .withEmail(email)
                .withIntroduction(request.getIntroduction())
                .withName(request.getName())
                .withSeason(request.getSeason().getCode())
                .withPrepTimeMin(request.getPrepTimeMin())
                .withCookTimeMin(request.getCookTimeMin())
                .withServingUnit(request.getServingUnit())
                .withServingQty(request.getServingQty())
                .withCreated(java.time.LocalDateTime.now())
                .build();

        if (request.getInstructionGroups() != null) {
            final var instructionGroups = request.getInstructionGroups().stream()
                    .map(instructionGroupDigest -> InstructionGroupMapper.create(recipe, instructionGroupDigest))
                    .collect(java.util.stream.Collectors.toSet());
            recipe.setInstructionGroups(instructionGroups);
        }

        return recipe;
    }

    public static void update(Recipe recipe, RecipeDigest request) throws UserServiceException {
        recipe.setIntroduction(request.getIntroduction());
        recipe.setName(request.getName());
        recipe.setSeason(request.getSeason().getCode());
        recipe.setPrepTimeMin(request.getPrepTimeMin());
        recipe.setCookTimeMin(request.getCookTimeMin());
        recipe.setServingUnit(request.getServingUnit());
        recipe.setServingQty(request.getServingQty());
        recipe.setUpdated(java.time.LocalDateTime.now());

        if (request.getInstructionGroups() == null) {
            recipe.setInstructionGroups(null);
        } else {
            Set<InstructionGroup> instructionGroups = new HashSet<>();

            for (var instructionGroupDigest : request.getInstructionGroups()) {
                if (instructionGroupDigest.getId() == null) {
                    instructionGroups.add(InstructionGroupMapper.create(recipe, instructionGroupDigest));
                } else {
                    final var instructionGroup = recipe.getInstructionGroups().stream()
                            .filter(x -> instructionGroupDigest.getId().equals(x.getId())).findFirst()
                            .orElse(null);
                    if (instructionGroup == null) {
                        log.error("Unable to find id " + instructionGroupDigest.getId());
                        throw new UserServiceException(UserServiceError.INVALID_PARAMETER);
                    }
                    instructionGroups.add(InstructionGroupMapper.update(instructionGroup, instructionGroupDigest));
                }
            }

            recipe.setInstructionGroups(instructionGroups);
        }
    }
}
