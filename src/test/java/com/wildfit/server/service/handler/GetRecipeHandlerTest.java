package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
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
        final var exception = assertThrows(UserServiceException.class,
                () -> GetRecipeHandler.builder()
                                      .withRecipe1Repository(recipe1Repository)
                                      .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var instructionGroup = InstructionGroupDigest.builder()
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
                                       .withInstructionGroups(List.of(instructionGroup))
                                       .build();

        createRecipe(recipe);

        final var recipeDigest = GetRecipeHandler.builder()
                                                 .withRecipe1Repository(recipe1Repository)
                                                 .withRecipeId(testRecipe.getId())
                                                 .build().execute();
        var instructionGroup1 = Iterables.getOnlyElement(recipeDigest.getInstructionGroups());
        assertTrue(instructionGroup1.getIngredients().isEmpty());
        assertEquals(2, instructionGroup1.getInstructions().size());

        final var recipeGroupId = instructionGroup1.getId();
        assertNotNull(recipeGroupId);
    }
}