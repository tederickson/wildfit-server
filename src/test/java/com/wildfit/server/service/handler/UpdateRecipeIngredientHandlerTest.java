package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.IngredientDigest;
import com.wildfit.server.domain.IngredientType;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.domain.UpdateIngredientRequest;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateRecipeIngredientHandlerTest extends CommonRecipeHandlerTest {
    static final String NAME = "UpdateRecipeIngredientHandlerTest";

    private IngredientDigest ingredientDigest;

    @BeforeEach
    void setUp() {
        super.setUp();

        ingredientDigest = IngredientDigest.builder()
                                           .withDescription(description)
                                           .withFoodName(foodName)
                                           .withIngredientServingQty(ingredientServingQty)
                                           .withIngredientServingUnit(ingredientServingUnit)
                                           .withIngredientType(IngredientType.DAIRY)
                                           .build();
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> UpdateRecipeIngredientHandler.builder().build().execute());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeIngredientHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRecipeIngredientRepository(recipeIngredientRepository)
                                                   .withRecipeId(-1L)
                                                   .withIngredientId(-89L)
                                                   .withRequest(UpdateIngredientRequest.builder().build())
                                                   .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingRecipeId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeIngredientHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRecipeIngredientRepository(recipeIngredientRepository)
                                                   .withUserId("-32L")
                                                   .withIngredientId(-89L)
                                                   .withRequest(UpdateIngredientRequest.builder().build())
                                                   .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingIngredientId() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeIngredientHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRecipeIngredientRepository(recipeIngredientRepository)
                                                   .withUserId("-32L")
                                                   .withRecipeId(-1L)
                                                   .withRequest(UpdateIngredientRequest.builder().build())
                                                   .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingRequest() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeIngredientHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRecipeIngredientRepository(recipeIngredientRepository)
                                                   .withUserId("-32L")
                                                   .withRecipeId(-1L)
                                                   .withIngredientId(-89L)
                                                   .build().execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void userNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeIngredientHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRecipeIngredientRepository(recipeIngredientRepository)
                                                   .withUserId("-14L")
                                                   .withRecipeId(-1L)
                                                   .withIngredientId(-89L)
                                                   .withRequest(UpdateIngredientRequest.builder().build())
                                                   .build().execute());

        assertEquals(UserServiceError.USER_NOT_FOUND, exception.getError());
    }

    @Test
    void recipeNotFound() {
        final var exception = assertThrows(UserServiceException.class,
                () -> UpdateRecipeIngredientHandler.builder()
                                                   .withUserRepository(userRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRecipeIngredientRepository(recipeIngredientRepository)
                                                   .withUserId(userId)
                                                   .withRecipeId(-1L)
                                                   .withIngredientId(-89L)
                                                   .withRequest(UpdateIngredientRequest.builder().build())
                                                   .build().execute());

        assertEquals(UserServiceError.RECIPE_NOT_FOUND, exception.getError());
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

        final var ingredient = CreateRecipeIngredientHandler.builder()
                                                            .withUserRepository(userRepository)
                                                            .withRecipeRepository(recipeRepository)
                                                            .withInstructionGroupRepository(instructionGroupRepository)
                                                            .withRecipeIngredientRepository(recipeIngredientRepository)
                                                            .withUserId(userId)
                                                            .withRecipeId(testRecipe.getId())
                                                            .withRecipeGroupId(recipeGroupId)
                                                            .withRequest(ingredientDigest)
                                                            .build().execute();
        assertNotNull(ingredient);

        final var updateIngredientRequest = UpdateIngredientRequest.builder()
                                                                   .withDescription("Updated Description")
                                                                   .withIngredientServingQty(1.11f)
                                                                   .withIngredientServingUnit("banana")
                                                                   .withIngredientType(IngredientType.PRODUCE)
                                                                   .build();
        ingredient.setDescription("Updated Description");
        ingredient.setIngredientServingQty(1.11f);
        ingredient.setIngredientServingUnit("banana");
        ingredient.setIngredientType(IngredientType.PRODUCE);

        final var updatedIngredient = UpdateRecipeIngredientHandler.builder()
                                                                   .withUserRepository(userRepository)
                                                                   .withRecipeRepository(recipeRepository)
                                                                   .withRecipeIngredientRepository(
                                                                           recipeIngredientRepository)
                                                                   .withUserId(userId)
                                                                   .withRecipeId(testRecipe.getId())
                                                                   .withIngredientId(ingredient.getId())
                                                                   .withRequest(updateIngredientRequest)
                                                                   .build().execute();

        assertEquals(ingredient, updatedIngredient);
    }
}