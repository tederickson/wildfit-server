package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeleteRecipeHandlerTest extends CommonRecipeHandlerTest {
    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> DeleteRecipeHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withInstructionGroupRepository(instructionGroupRepository)
                        .withRecipeId(-1L)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withInstructionGroupRepository(instructionGroupRepository)
                        .withUserId(-14L)
                        .withRecipeId(-1L)
                        .build().execute());
        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void execute() throws UserServiceException {
        final var name = "TEST TEST TEST";

        final var step1 = InstructionDigest.builder().withStepNumber(1).withInstruction("Heat the oil").build();

        final var instructionGroup = InstructionGroupDigest.builder()
                .withInstructionGroupNumber(1)
                .withInstructions(List.of(step1)).build();
        final var recipe = RecipeDigest.builder()
                .withName(name)
                .withSeason(SeasonType.FALL)
                .withIntroduction("introduction")
                .withPrepTimeMin(5)
                .withCookTimeMin(15)
                .withServingQty(4)
                .withServingUnit("serving")
                .withInstructionGroups(List.of(instructionGroup))
                .build();
        final var response = CreateRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withUserId(userId)
                .withRequest(recipe)
                .build().execute();
        assertNotNull(response);

        final var dbRecipeId = response.getId();
        final var dbRecipe = recipeRepository.findById(dbRecipeId).orElseThrow();
        assertEquals(name, dbRecipe.getName());

        final var dbInstructionGroup = Iterables.getOnlyElement(instructionGroupRepository.findByRecipeId(dbRecipeId));

        for (var dbInstruction : dbInstructionGroup.getInstructions()) {
            assertEquals(dbInstructionGroup, dbInstruction.getInstructionGroup());
            assertTrue(dbInstruction.getStepNumber() > 0);
            assertNotNull(dbInstruction.getText());
        }
        DeleteRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withUserId(userId)
                .withRecipeId(dbRecipeId)
                .build().execute();

        assertTrue(recipeRepository.findById(dbRecipeId).isEmpty());
        assertTrue(instructionGroupRepository.findByRecipeId(dbRecipeId).isEmpty());
    }

}