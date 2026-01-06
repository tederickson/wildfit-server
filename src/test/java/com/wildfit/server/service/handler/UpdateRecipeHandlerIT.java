package com.wildfit.server.service.handler;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UpdateRecipeHandlerIT extends CommonRecipeHandlerTest {

    static final String NAME = "UpdateRecipeHandlerTest";

    private static List<IngredientDigest> getIngredients(RecipeDigest recipe) {
        return recipe
                .getRecipeGroups().stream()
                .map(RecipeGroupDigest::ingredients)
                .flatMap(Collection::stream)
                .toList();
    }

    private static List<InstructionDigest> getInstructions(RecipeDigest recipe) {
        return recipe
                .getRecipeGroups().stream()
                .map(RecipeGroupDigest::instructions)
                .flatMap(Collection::stream)
                .toList();
    }

    @Test
    void changeSummary() throws WildfitServiceException {
        final var instructionGroup = RecipeGroupDigest.builder()
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
                .withRecipeGroups(List.of(instructionGroup))
                .build();

        createRecipe(recipe);

        assertEquals(1, testRecipe.getRecipeGroups().size());
        assertEquals(2, testRecipe.getRecipeGroups().getFirst().instructions().size());

        testRecipe.setIntroduction("blah blah blah");
        testRecipe.setName("name");
        testRecipe.setSeason(SeasonType.FALL);
        testRecipe.setPrepTimeMin(100);
        testRecipe.setCookTimeMin(200);
        testRecipe.setServingQty(13);
        testRecipe.setServingUnit("pound");

        final var response = updateRecipe(testRecipe);

        assertEquals(1, response.getRecipeGroups().size());
        assertEquals(2, response.getRecipeGroups().getFirst().instructions().size());

        assertEquals("blah blah blah", response.getIntroduction());
        assertEquals("name", response.getName());
        assertEquals(SeasonType.FALL, response.getSeason());
        assertEquals(100, response.getPrepTimeMin());
        assertEquals(200, response.getCookTimeMin());
        assertEquals(13, response.getServingQty());
        assertEquals("pound", response.getServingUnit());
    }

    @Test
    void removeAllInstructionGroups() throws WildfitServiceException {
        final var recipe = ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json");

        createRecipe(recipe);

        testRecipe.setRecipeGroups(List.of());
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void addRecipeGroup() throws WildfitServiceException {
        final var recipeGroupDigest = RecipeGroupDigest.builder()
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
                .withRecipeGroups(List.of(recipeGroupDigest))
                .build();

        createRecipe(recipe);

        assertEquals(1, testRecipe.getRecipeGroups().size());
        var recipeGroup = testRecipe.getRecipeGroups().getFirst();
        assertEquals(2, recipeGroup.instructions().size());
        assertEquals(0, recipeGroup.ingredients().size());

        final var recipeGroupDigest1 = ReadRecipeDigest.getRecipeDigest("Tuna_salad.json");
        assertEquals(2, recipeGroupDigest1.getRecipeGroups().size());

        testRecipe.getRecipeGroups().addAll(recipeGroupDigest1.getRecipeGroups());
        assertEquals(3, testRecipe.getRecipeGroups().size());

        final var response = updateRecipe(testRecipe);

        assertEquals(3, response.getRecipeGroups().size());
    }

    @Test
    void addInstructionsAndIngredientsToRecipeGroup() throws WildfitServiceException {
        final var recipeGroupDigest = RecipeGroupDigest.builder()
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
                .withRecipeGroups(List.of(recipeGroupDigest))
                .build();

        createRecipe(recipe);

        final var recipeGroupSize = 1;
        final var originalInstructionSize = 2;
        final var originalIngredientSize = 0;

        assertEquals(recipeGroupSize, testRecipe.getRecipeGroups().size());
        var recipeGroup = testRecipe.getRecipeGroups().getFirst();
        assertEquals(originalInstructionSize, recipeGroup.instructions().size());
        assertEquals(originalIngredientSize, recipeGroup.ingredients().size());

        final var recipeGroupDigest1 = ReadRecipeDigest.getRecipeDigest("Chili_Beef_Lettuce_Wraps__Summer_.json");
        assertEquals(1, recipeGroupDigest1.getRecipeGroups().size());

        final var chiliRecipeGroup = recipeGroupDigest1.getRecipeGroups().getFirst();

        assertEquals(4, chiliRecipeGroup.instructions().size());
        assertEquals(10, chiliRecipeGroup.ingredients().size());

        recipeGroup.ingredients().addAll(chiliRecipeGroup.ingredients());
        recipeGroup.instructions().addAll(chiliRecipeGroup.instructions());

        final var response = updateRecipe(testRecipe);


        assertEquals(recipeGroupSize, response.getRecipeGroups().size());
        recipeGroup = response.getRecipeGroups().getFirst();

        assertEquals(originalInstructionSize + 4, recipeGroup.instructions().size());
        assertEquals(originalIngredientSize + 10, recipeGroup.ingredients().size());
    }

    @Test
    void removeRecipeGroup() throws WildfitServiceException {
        final var instructionGroup1 = RecipeGroupDigest.builder()
                .withInstructionGroupNumber(1)
                .withInstructions(List.of(step1, step2, step3, step4))
                .build();
        final var instructionGroup2 = RecipeGroupDigest.builder()
                .withInstructionGroupNumber(2)
                .withInstructions(List.of(step1, step4))
                .build();
        final var recipe = RecipeDigest.builder()
                .withName(NAME)
                .withSeason(SeasonType.FALL)
                .withIntroduction(INTRODUCTION)
                .withPrepTimeMin(5)
                .withCookTimeMin(15)
                .withServingQty(4)
                .withServingUnit("serving")
                .withRecipeGroups(List.of(instructionGroup1, instructionGroup2))
                .build();

        createRecipe(recipe);

        testRecipe.getRecipeGroups().removeFirst();
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void removeInstruction() throws WildfitServiceException {
        final var instructionGroup = RecipeGroupDigest.builder()
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
                .withRecipeGroups(List.of(instructionGroup))
                .build();

        createRecipe(recipe);

        testRecipe.getRecipeGroups().getFirst().instructions().remove(2);
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void updateInstruction() throws WildfitServiceException {
        final var instructionGroup = RecipeGroupDigest.builder()
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
                .withRecipeGroups(List.of(instructionGroup))
                .build();

        createRecipe(recipe);

        InstructionDigest instruction = testRecipe.getRecipeGroups().getFirst().instructions().get(2);
        var updatedInstruction = InstructionDigest.builder()
                .withInstruction("CHANGED!")
                .withStepNumber(instruction.stepNumber())
                .withId(instruction.id()).build();
        testRecipe.getRecipeGroups().getFirst().instructions().set(2, updatedInstruction);

        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void addInstructionsAndIngredients() throws WildfitServiceException {
        final var recipe = ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json");
        final var recipe2 = ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json");

        createRecipe(recipe);

        testRecipe.getRecipeGroups().addAll(recipe2.getRecipeGroups());


        final var response = updateRecipe(testRecipe);

        assertEquals(recipe.getRecipeGroups().size() + recipe2.getRecipeGroups().size(),
                     response.getRecipeGroups().size());

        final var recipeIngredients = getIngredients(recipe);
        final var recipe2Ingredients = getIngredients(recipe2);
        final var responseIngredients = getIngredients(response);

        assertEquals(recipeIngredients.size() + recipe2Ingredients.size(), responseIngredients.size());

        final var recipeInstructions = getInstructions(recipe);
        final var recipe2Instructions = getInstructions(recipe2);
        final var responseInstructions = getInstructions(response);

        assertEquals(recipeInstructions.size() + recipe2Instructions.size(), responseInstructions.size());
    }

    @Test
    void updateRecipe_missingRequest() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> UpdateRecipeHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withUserId(userId)
                                                   .build().execute());
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void updateRecipe_missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> UpdateRecipeHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRequest(RecipeDigest.builder()
                                                                        .withSeason(SeasonType.SUMMER).build())
                                                   .build().execute());
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void updateRecipe_missingSeason() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> UpdateRecipeHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withUserId(userId)
                                                   .withRequest(RecipeDigest.builder().build())
                                                   .build().execute());
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    private RecipeDigest updateRecipe(RecipeDigest testRecipe) throws
            WildfitServiceException {
        return recipeService.updateRecipe(userId, testRecipe);
    }
}