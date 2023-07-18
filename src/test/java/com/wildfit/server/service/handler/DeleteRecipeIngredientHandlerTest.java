package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
class DeleteRecipeIngredientHandlerTest extends CommonRecipeHandlerTest {
    static final String NAME = "DeleteRecipeIngredientHandlerTest";

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> DeleteRecipeIngredientHandler.builder().build().execute());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withRecipeId(-1L)
                        .withIngredientId(-89L)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingRecipeId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withUserId("-15L")
                        .withIngredientId(-89L)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingIngredientId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withUserId("-15L")
                        .withRecipeId(-1L)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> DeleteRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withUserId("-14L")
                        .withRecipeId(-1L)
                        .withIngredientId(-89L)
                        .build().execute());

        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void ingredientNotFound() throws UserServiceException {
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

        DeleteRecipeIngredientHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withUserId(userId)
                .withRecipeId(testRecipe.getId())
                .withIngredientId(-89L)
                .build().execute();
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

        final var dbInstructionGroup = Iterables.getOnlyElement(testRecipe.getInstructionGroups());
        final var recipeGroupId = dbInstructionGroup.getId();
        assertNotNull(recipeGroupId);

        final var ingredientDigest = IngredientDigest.builder()
                .withAddedSugars(addedSugars)
                .withBrandName(brandName)
                .withBrandNameItemName(brandNameItemName)
                .withCalcium(calcium)
                .withCalories(calories)
                .withCholesterol(cholesterol)
                .withDescription(description)
                .withDietaryFiber(dietaryFiber)
                .withFoodName(foodName)

                .withIngredientServingQty(ingredientServingQty)
                .withIngredientServingUnit(ingredientServingUnit)

                .withIron(iron)
                .withMetricQty(metricQty)
                .withMetricUom(metricUom)
                .withNixBrandId(nixBrandId)
                .withNixBrandName(nixBrandName)
                .withNixItemId(nixItemId)
                .withPhosphorus(phosphorus)
                .withPhoto(photo)
                .withPotassium(potassium)
                .withProtein(protein)

                .withSaturatedFat(saturatedFat)
                .withServingQty(servingQty)
                .withServingUnit(servingUnit)
                .withServingWeightGrams(servingWeightGrams)
                .withSodium(sodium)
                .withSugars(sugars)
                .withTotalCarbohydrate(totalCarbohydrate)
                .withTotalFat(totalFat)
                .withTransFattyAcid(transFattyAcid)
                .withVitaminD(vitaminD)
                .build();

        final var response = CreateRecipeIngredientHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withUserId(userId)
                .withRecipeId(testRecipe.getId())
                .withRecipeGroupId(recipeGroupId)
                .withRequest(ingredientDigest)
                .build().execute();
        assertNotNull(response);


        var recipeDigest = GetRecipeHandler.builder()
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withRecipeId(testRecipe.getId())
                .build().execute();

        assertEquals(1, recipeDigest.getInstructionGroups().get(0).getIngredients().size());

        DeleteRecipeIngredientHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withUserId(userId)
                .withRecipeId(testRecipe.getId())
                .withIngredientId(response.getId())
                .build().execute();

        recipeDigest = GetRecipeHandler.builder()
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withRecipeId(testRecipe.getId())
                .build().execute();

        assertEquals(0, recipeDigest.getInstructionGroups().get(0).getIngredients().size());
    }
}