package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import com.wildfit.server.model.mapper.IngredientDigestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateRecipeHandlerTest extends CommonRecipeHandlerTest {

    @Test
    void execute() throws UserServiceException, java.io.IOException {
        final var season = SeasonType.SPRING;
        final var name = "CreateRecipeHandlerTest";

        final var instructionGroup = InstructionGroupDigest.builder()
                .withInstructionGroupNumber(1)
                .withInstructions(List.of(step1, step2, step3, step4)).build();
        final var recipe = RecipeDigest.builder()
                .withName(name)
                .withSeason(season)
                .withIntroduction(INTRODUCTION)
                .withPrepTimeMin(5)
                .withCookTimeMin(15)
                .withServingQty(4)
                .withServingUnit("serving")
                .withInstructionGroups(List.of(instructionGroup))
                .build();
        createRecipe(recipe);

        assertNotNull(testRecipe);

        final var dbRecipeId = testRecipe.getId();
        final var dbRecipeGroupId = Iterables.getOnlyElement(testRecipe.getInstructionGroups()).getId();
        final var dbRecipe = recipeRepository.findById(dbRecipeId).orElseThrow();
        assertEquals(name, dbRecipe.getName());
        assertEquals(SeasonType.SPRING.getCode(), dbRecipe.getSeason());

        final var dbInstructionGroup = Iterables.getOnlyElement(instructionGroupRepository.findByRecipeId(dbRecipeId));

        for (var dbInstruction : dbInstructionGroup.getInstructions()) {
            assertEquals(dbInstructionGroup, dbInstruction.getInstructionGroup());
            assertTrue(dbInstruction.getStepNumber() > 0);
            assertNotNull(dbInstruction.getText());
        }

        var foodItems = getFoodItems("load/coconut_oil.json");
        var foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

        var ingredient = CreateRecipeIngredientHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withUserId(userId)
                .withRecipeId(dbRecipeId)
                .withRecipeGroupId(dbRecipeGroupId)
                .withRequest(IngredientDigestMapper.create(foodItemDigest,
                        "2 tsp neutral-flavored oil like coconut, avocado or walnut", 2f, "tsp"))
                .build().execute();
        assertNotNull(ingredient);
        assertEquals("coconut oil", ingredient.getFoodName());

        foodItems = getFoodItems("load/cilantro.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

        ingredient = CreateRecipeIngredientHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withUserId(userId)
                .withRecipeId(dbRecipeId)
                .withRecipeGroupId(dbRecipeGroupId)
                .withRequest(IngredientDigestMapper.create(foodItemDigest,
                        "1/2 cup chopped cilantro", 0.25f, "cup"))
                .build().execute();
        assertNotNull(ingredient);
        assertEquals("cilantro", ingredient.getFoodName());
        assertEquals(0.25f, ingredient.getIngredientServingQty(), 0.01);
        assertEquals(0.5f, ingredient.getServingQty(), 0.01);
    }

    private FoodItems getFoodItems(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            return mapper.readValue(in, FoodItems.class);
        }
    }
}