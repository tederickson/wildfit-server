package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateRecipeHandlerTest extends CommonRecipeHandlerTest {

    static final String NAME = "UpdateRecipeHandlerTest";

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

        assertEquals(2, testRecipe.getInstructionGroups().get(0).getInstructions().size());

        testRecipe.setIntroduction("blah blah blah");
        testRecipe.getInstructionGroups().get(0).getInstructions().addAll(List.of(step3, step4));

        final var response = updateRecipe(testRecipe);

        // Update the ids of step3 and step4
        final var dbInstructionGroups = instructionGroupRepository.findByRecipeId(testRecipe.getId());

        for (var dbInstructionGroup : dbInstructionGroups) {
            for (var instruction : dbInstructionGroup.getInstructions()) {
                switch (instruction.getStepNumber()) {
                    case 3 -> step3.setId(instruction.getId());
                    case 4 -> step4.setId(instruction.getId());
                }
            }
        }

        assertEquals(testRecipe, response);
    }

    @Test
    void addInstructionGroup() throws UserServiceException {
        final var instructionGroup = InstructionGroupDigest.builder()
                                                           .withInstructionGroupNumber(1)
                                                           .withInstructions(List.of(step1, step2)).build();
        final var recipe = RecipeDigest.builder()
                                       .withName(NAME)
                                       .withSeason(SeasonType.FALL)
                                       .withIntroduction(INTRODUCTION)
                                       .withPrepTimeMin(5)
                                       .withCookTimeMin(15)
                                       .withServingQty(4)
                                       .withServingUnit("serving")
                                       .withInstructionGroups(List.of(instructionGroup))
                                       .build();

        createRecipe(recipe);

        final var instructionGroup2 = InstructionGroupDigest.builder()
                                                            .withInstructionGroupNumber(2)
                                                            .withInstructions(List.of(step3, step4)).build();

        testRecipe.getInstructionGroups().add(instructionGroup2);
        final var response = updateRecipe(testRecipe);

        // Update the ids of instruction group 2
        final var dbInstructionGroups = instructionGroupRepository.findByRecipeId(testRecipe.getId());

        for (var dbInstructionGroup : dbInstructionGroups) {
            if (dbInstructionGroup.getInstructionGroupNumber() == 2) {
                final var group2 = testRecipe.getInstructionGroups().get(1);
                group2.setId(dbInstructionGroup.getId());

                for (var instruction : dbInstructionGroup.getInstructions()) {
                    switch (instruction.getStepNumber()) {
                        case 3 -> step3.setId(instruction.getId());
                        case 4 -> step4.setId(instruction.getId());
                    }
                }
            }
        }

        assertEquals(testRecipe, response);
    }

    @Test
    void removeInstructionGroup() throws UserServiceException {
        final var instructionGroup = InstructionGroupDigest.builder()
                                                           .withInstructionGroupNumber(1)
                                                           .withInstructions(List.of(step1, step2)).build();
        final var recipe = RecipeDigest.builder()
                                       .withName(NAME)
                                       .withSeason(SeasonType.FALL)
                                       .withIntroduction(INTRODUCTION)
                                       .withPrepTimeMin(5)
                                       .withCookTimeMin(15)
                                       .withServingQty(4)
                                       .withServingUnit("serving")
                                       .withInstructionGroups(List.of(instructionGroup))
                                       .build();

        createRecipe(recipe);

        testRecipe.setInstructionGroups(List.of());
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void removeInstruction() throws UserServiceException {
        final var instructionGroup = InstructionGroupDigest.builder()
                                                           .withInstructionGroupNumber(1)
                                                           .withInstructions(List.of(step1, step2, step3, step4))
                                                           .build();
        final var recipe = RecipeDigest.builder()
                                       .withName(NAME)
                                       .withSeason(SeasonType.FALL)
                                       .withIntroduction(INTRODUCTION)
                                       .withPrepTimeMin(5)
                                       .withCookTimeMin(15)
                                       .withServingQty(4)
                                       .withServingUnit("serving")
                                       .withInstructionGroups(List.of(instructionGroup))
                                       .build();

        createRecipe(recipe);

        testRecipe.getInstructionGroups().get(0).getInstructions().remove(2);
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void updateInstruction() throws UserServiceException {
        final var instructionGroup = InstructionGroupDigest.builder()
                                                           .withInstructionGroupNumber(1)
                                                           .withInstructions(List.of(step1, step2, step3, step4))
                                                           .build();
        final var recipe = RecipeDigest.builder()
                                       .withName(NAME)
                                       .withSeason(SeasonType.FALL)
                                       .withIntroduction(INTRODUCTION)
                                       .withPrepTimeMin(5)
                                       .withCookTimeMin(15)
                                       .withServingQty(4)
                                       .withServingUnit("serving")
                                       .withInstructionGroups(List.of(instructionGroup))
                                       .build();

        createRecipe(recipe);

        testRecipe.getInstructionGroups().get(0).getInstructions().get(2).setInstruction("CHANGED!");
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    private RecipeDigest updateRecipe(RecipeDigest testRecipe) throws UserServiceException {
        return UpdateRecipeHandler.builder()
                                  .withUserRepository(userRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withInstructionGroupRepository(instructionGroupRepository)
                                  .withInstructionRepository(instructionRepository)
                                  .withUserId(userId)
                                  .withRequest(testRecipe)
                                  .build().execute();
    }
}