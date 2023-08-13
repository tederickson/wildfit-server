package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateRecipeHandlerTest extends CommonRecipeHandlerTest {

    static final String NAME = "UpdateRecipeHandlerTest";

    private static List<IngredientDigest> getIngredients(RecipeDigest recipe) {
        return recipe
                .getInstructionGroups().stream()
                .map(InstructionGroupDigest::getIngredients)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<InstructionDigest> getInstructions(RecipeDigest recipe) {
        return recipe
                .getInstructionGroups().stream()
                .map(InstructionGroupDigest::getInstructions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Test
    void changeSummary() throws UserServiceException {
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

        assertEquals(1, testRecipe.getInstructionGroups().size());
        assertEquals(2, testRecipe.getInstructionGroups().get(0).getInstructions().size());

        testRecipe.setIntroduction("blah blah blah");
        testRecipe.setName("name");
        testRecipe.setSeason(SeasonType.FALL);
        testRecipe.setPrepTimeMin(100);
        testRecipe.setCookTimeMin(200);
        testRecipe.setServingQty(13);
        testRecipe.setServingUnit("pound");

        final var response = updateRecipe(testRecipe);

        assertEquals(1, response.getInstructionGroups().size());
        assertEquals(2, response.getInstructionGroups().get(0).getInstructions().size());

        assertEquals("blah blah blah", response.getIntroduction());
        assertEquals("name", response.getName());
        assertEquals(SeasonType.FALL, response.getSeason());
        assertEquals(100, response.getPrepTimeMin());
        assertEquals(200, response.getCookTimeMin());
        assertEquals(13, response.getServingQty());
        assertEquals("pound", response.getServingUnit());
    }

    @Test
    void removeAllInstructionGroups() throws UserServiceException {
        final var recipe = getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json");

        createRecipe(recipe);

        testRecipe.setInstructionGroups(List.of());
        final var response = updateRecipe(testRecipe);

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

        assertEquals(1, testRecipe.getInstructionGroups().size());
        var recipeGroup = testRecipe.getInstructionGroups().get(0);
        assertEquals(2, recipeGroup.getInstructions().size());
        assertEquals(0, recipeGroup.getIngredients().size());

        final var instructionGroup2 = InstructionGroupDigest.builder()
                                                            .withInstructionGroupNumber(2)
                                                            .withInstructions(List.of(step3, step4, step1))
                                                            .withIngredients(List.of())
                                                            .build();

        testRecipe.getInstructionGroups().add(instructionGroup2);
        final var response = updateRecipe(testRecipe);

        assertEquals(2, response.getInstructionGroups().size());
        for (var group : response.getInstructionGroups()) {
            if (group.getInstructionGroupNumber() == 1) {
                assertEquals(testRecipe.getInstructionGroups().get(0), group);
            } else {
                assertEquals(3, group.getInstructions().size());
                assertEquals(0, group.getIngredients().size());
            }
        }
    }

    @Test
    void removeRecipeGroup() throws UserServiceException {
        final var instructionGroup1 = InstructionGroupDigest.builder()
                                                            .withInstructionGroupNumber(1)
                                                            .withInstructions(List.of(step1, step2, step3, step4))
                                                            .build();
        final var instructionGroup2 = InstructionGroupDigest.builder()
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
                                       .withInstructionGroups(List.of(instructionGroup1, instructionGroup2))
                                       .build();

        createRecipe(recipe);

        testRecipe.getInstructionGroups().remove(0);
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

    @Test
    void addInstructionsAndIngredients() throws UserServiceException {
        final var recipe = getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json");
        final var recipe2 = getRecipeDigest("Tuna_salad_with_apple_and_celery.json");

        createRecipe(recipe);

        testRecipe.getInstructionGroups().addAll(recipe2.getInstructionGroups());


        final var response = updateRecipe(testRecipe);

        assertEquals(recipe.getInstructionGroups().size() + recipe2.getInstructionGroups().size(),
                response.getInstructionGroups().size());

        final var recipeIngredients = getIngredients(recipe);
        final var recipe2Ingredients = getIngredients(recipe2);
        final var responseIngredients = getIngredients(response);

        assertEquals(recipeIngredients.size() + recipe2Ingredients.size(), responseIngredients.size());

        final var recipeInstructions = getInstructions(recipe);
        final var recipe2Instructions = getInstructions(recipe2);
        final var responseInstructions = getInstructions(response);

        assertEquals(recipeInstructions.size() + recipe2Instructions.size(), responseInstructions.size());
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