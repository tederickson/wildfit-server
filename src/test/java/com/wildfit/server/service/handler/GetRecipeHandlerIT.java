package com.wildfit.server.service.handler;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GetRecipeHandlerIT extends CommonRecipeHandlerTest {
    private static final String NAME = "GetRecipeHandlerTest";

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> GetRecipeHandler.builder().build().execute());
    }

    @Test
    void missingRecipeId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> recipeService.retrieveRecipe(null));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void invalidRecipeId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> recipeService.retrieveRecipe(-7L));
        assertEquals(WildfitServiceError.RECIPE_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws WildfitServiceException {
        final var recipeGroupDigest = RecipeGroupDigest.builder()
                .withInstructionGroupNumber(1)
                .withInstructions(List.of(step1, step2)).build();
        final var recipe = RecipeDigest.builder()
                .withName(NAME)
                .withSeason(SeasonType.SPRING)
                .withIntroduction(INTRODUCTION)
                .withPrepTimeMin(5)
                .withCookTimeMin(15)
                .withServingQty(4)
                .withServingUnit("serving")
                .withRecipeGroups(List.of(recipeGroupDigest))
                .build();

        createRecipe(recipe);

        final var recipeDigest = recipeService.retrieveRecipe(testRecipe.getId());

        var instructionGroup1 = Iterables.getOnlyElement(recipeDigest.getRecipeGroups());
        assertTrue(instructionGroup1.ingredients().isEmpty());
        assertEquals(2, instructionGroup1.instructions().size());

        final var recipeGroupId = instructionGroup1.id();
        assertNotNull(recipeGroupId);
    }
}