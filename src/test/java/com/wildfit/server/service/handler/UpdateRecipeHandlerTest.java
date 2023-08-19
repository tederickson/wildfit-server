package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeGroupDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateRecipeHandlerTest extends CommonRecipeHandlerTest {

    static final String NAME = "UpdateRecipeHandlerTest";

    private static List<IngredientDigest> getIngredients(RecipeDigest recipe) {
        return recipe
                .getRecipeGroups().stream()
                .map(RecipeGroupDigest::getIngredients)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<InstructionDigest> getInstructions(RecipeDigest recipe) {
        return recipe
                .getRecipeGroups().stream()
                .map(RecipeGroupDigest::getInstructions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Test
    void changeSummary() throws UserServiceException {
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
        assertEquals(2, testRecipe.getRecipeGroups().get(0).getInstructions().size());

        testRecipe.setIntroduction("blah blah blah");
        testRecipe.setName("name");
        testRecipe.setSeason(SeasonType.FALL);
        testRecipe.setPrepTimeMin(100);
        testRecipe.setCookTimeMin(200);
        testRecipe.setServingQty(13);
        testRecipe.setServingUnit("pound");

        final var response = updateRecipe(testRecipe);

        assertEquals(1, response.getRecipeGroups().size());
        assertEquals(2, response.getRecipeGroups().get(0).getInstructions().size());

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

        testRecipe.setRecipeGroups(List.of());
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void addInstructionGroup() throws UserServiceException {
        final var instructionGroup = RecipeGroupDigest.builder()
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
                                       .withRecipeGroups(List.of(instructionGroup))
                                       .build();

        createRecipe(recipe);

        assertEquals(1, testRecipe.getRecipeGroups().size());
        var recipeGroup = testRecipe.getRecipeGroups().get(0);
        assertEquals(2, recipeGroup.getInstructions().size());
        assertEquals(0, recipeGroup.getIngredients().size());

        final var instructionGroup2 = RecipeGroupDigest.builder()
                                                       .withInstructionGroupNumber(2)
                                                       .withInstructions(List.of(step3, step4, step1))
                                                       .withIngredients(List.of())
                                                       .build();

        testRecipe.getRecipeGroups().add(instructionGroup2);
        final var response = updateRecipe(testRecipe);

        assertEquals(2, response.getRecipeGroups().size());
        for (var group : response.getRecipeGroups()) {
            if (group.getInstructionGroupNumber() == 1) {
                assertEquals(testRecipe.getRecipeGroups().get(0), group);
            } else {
                assertEquals(3, group.getInstructions().size());
                assertEquals(0, group.getIngredients().size());
            }
        }
    }

    @Test
    void removeRecipeGroup() throws UserServiceException {
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

        testRecipe.getRecipeGroups().remove(0);
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void removeInstruction() throws UserServiceException {
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

        testRecipe.getRecipeGroups().get(0).getInstructions().remove(2);
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void updateInstruction() throws UserServiceException {
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

        testRecipe.getRecipeGroups().get(0).getInstructions().get(2).setInstruction("CHANGED!");
        final var response = updateRecipe(testRecipe);

        assertEquals(testRecipe, response);
    }

    @Test
    void addInstructionsAndIngredients() throws UserServiceException {
        final var recipe = getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json");
        final var recipe2 = getRecipeDigest("Tuna_salad_with_apple_and_celery.json");

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
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withRecipeRepository(recipeRepository)
                                         .withUserId(userId)
                                         .build().execute());
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void updateRecipe_missingUserId() {
        final var exception = assertThrows(UserServiceException.class,
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
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withRecipeRepository(recipeRepository)
                                         .withUserId(userId)
                                         .withRequest(RecipeDigest.builder().build())
                                         .build().execute());
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    private RecipeDigest updateRecipe(RecipeDigest testRecipe) throws UserServiceException {
        return UpdateRecipeHandler.builder()
                                  .withUserRepository(userRepository)
                                  .withRecipeRepository(recipeRepository)
                                  .withUserId(userId)
                                  .withRequest(testRecipe)
                                  .build().execute();
    }
}