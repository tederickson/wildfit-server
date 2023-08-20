package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetRecipeHandlerTest extends CommonRecipeHandlerTest {
    static final String NAME = "GetRecipeHandlerTest";

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> GetRecipeHandler.builder().build().execute());
    }

    @Test
    void missingRecipeId() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> GetRecipeHandler.builder()
                                      .withRecipeRepository(recipeRepository)
                                      .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
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

        final var recipeDigest = GetRecipeHandler.builder()
                                                 .withRecipeRepository(recipeRepository)
                                                 .withRecipeId(testRecipe.getId())
                                                 .build().execute();
        var instructionGroup1 = Iterables.getOnlyElement(recipeDigest.getRecipeGroups());
        assertTrue(instructionGroup1.getIngredients().isEmpty());
        assertEquals(2, instructionGroup1.getInstructions().size());

        final var recipeGroupId = instructionGroup1.getId();
        assertNotNull(recipeGroupId);
    }
}