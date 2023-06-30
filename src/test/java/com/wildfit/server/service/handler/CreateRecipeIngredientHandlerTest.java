package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.PhotoDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateRecipeIngredientHandlerTest extends AbstractRecipeHandlerTest {
    static final String NAME = "CreateRecipeIngredientHandlerTest";

    static final String foodName = "Butter, Pure Irish, Unsalted";
    static final String description = "1-2 teaspoons Kerrygold unsalted butter";
    static final String brandName = "Kerrygold";
    static final String brandNameItemName = "Pasteurized Cream, Skimmed Milk, Cultures.";
    static final Float servingQty = 1f;
    static final String servingUnit = "tbsp";
    static final Float ingredientServingQty = 3f;
    static final String ingredientServingUnit = "tablespoon";
    static final Float servingWeightGrams = 18f;
    static final Float metricQty = 14f;
    static final String metricUom = "g";
    static final Float calories = 100f;
    static final Float totalFat = 12f;
    static final Float saturatedFat = 8f;
    static final Float cholesterol = 30f;
    static final Float sodium = 0.1f;
    static final Float totalCarbohydrate = 0.2f;
    static final Float dietaryFiber = 0.3f;
    static final Float sugars = 0.4f;
    static final Float protein = 0.5f;
    static final Float potassium = 0.6f;
    static final Float phosphorus = 0.7f;
    static final Float calcium = 0.8f;
    static final Float iron = 0.9f;
    static final Float vitaminD = 1.1f;
    static final Float addedSugars = 1.2f;
    static final Float transFattyAcid = 1.3f;
    static final String nixBrandName = "Kerrygold Unsalted Irish Butter";
    static final String nixBrandId = "51db37b7176fe9790a8989b4";
    static final String nixItemId = "52a15041d122497b50000a75";
    static final PhotoDigest photo = PhotoDigest.builder()
            .withThumb("https://nutritionix-api.s3.amazonaws.com/62ee4a5ea58c4000088c940a.jpeg").build();

    private IngredientDigest ingredientDigest;

    @BeforeEach
    void setUp() {
        super.setUp();

        ingredientDigest = IngredientDigest.builder()
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
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> CreateRecipeIngredientHandler.builder().build().execute());
    }

    @Test
    void missingId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withInstructionGroupRepository(instructionGroupRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withRecipeId(-1L)
                        .withRecipeGroupId(-89L)
                        .withRequest(ingredientDigest)
                        .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> CreateRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withInstructionGroupRepository(instructionGroupRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withUserId(-14L)
                        .withRecipeId(-1L)
                        .withRecipeGroupId(-89L)
                        .withRequest(ingredientDigest)
                        .build().execute());

        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void recipeGroupNotFound() throws UserServiceException {
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

        final var exception = assertThrows(UserServiceException.class,
                () -> CreateRecipeIngredientHandler.builder()
                        .withUserRepository(userRepository)
                        .withRecipeRepository(recipeRepository)
                        .withInstructionGroupRepository(instructionGroupRepository)
                        .withRecipeIngredientRepository(recipeIngredientRepository)
                        .withUserId(userId)
                        .withRecipeId(testRecipe.getId())
                        .withRecipeGroupId(-89L)
                        .withRequest(ingredientDigest)
                        .build().execute());

        assertEquals(UserServiceError.RECIPE_GROUP_NOT_FOUND, exception.getError());
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

        ingredientDigest.setId(response.getId());
        ingredientDigest.setRecipeId(testRecipe.getId());
        ingredientDigest.setInstructionGroupId(recipeGroupId);

        assertEquals(ingredientDigest, response);
    }
}