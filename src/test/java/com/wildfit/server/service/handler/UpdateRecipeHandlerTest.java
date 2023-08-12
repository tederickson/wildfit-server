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

        System.out.println("response = " + response);
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
                                                            .withInstructions(List.of(step3, step4))
                                                            .withIngredients(List.of())
                                                            .build();

        testRecipe.getInstructionGroups().add(instructionGroup2);
        final var response = updateRecipe(testRecipe);


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
                                  .withRecipe1Repository(recipe1Repository)
                                  .withUserId(userId)
                                  .withRequest(testRecipe)
                                  .build().execute();
    }
}