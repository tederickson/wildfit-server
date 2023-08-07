package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.wildfit.server.domain.IngredientType;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import com.wildfit.server.model.mapper.IngredientDigestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateRecipeHandlerTest extends CommonRecipeHandlerTest {

    @Test
    void execute() throws Exception {
        final var season = SeasonType.SPRING;
        final var name = "CreateRecipeHandlerTest";

        final var instructionGroup = InstructionGroupDigest.builder()
                                                           .withInstructionGroupNumber(1)
                                                           .withInstructions(List.of(step1, step2, step3, step4))
                                                           .build();
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
        assertEquals(Season.SPRING.getCode(), dbRecipe.getSeason());

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
                                                              "2 tsp neutral-flavored oil like coconut, avocado or walnut",
                                                              2f, "tsp", null))
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
                                                          "1/2 cup chopped cilantro", 0.25f, "cup",
                                                          IngredientType.PRODUCE))
                                                  .build().execute();
        assertNotNull(ingredient);
        assertEquals("cilantro", ingredient.getFoodName());
        assertEquals(0.25f, ingredient.getIngredientServingQty(), 0.01);
    }

    @Test
    public void processTuna_salad() throws Exception {
        final var digest = getRecipeDigest("Tuna_salad.json");
        validateTunaSalad(digest);

        createRecipe(digest);

        validateTunaSalad(testRecipe);
    }

    private static void validateTunaSalad(com.wildfit.server.domain.RecipeDigest digest) {
        assertNotNull(digest);

        assertEquals("Tuna salad", digest.getName());
        assertEquals(com.wildfit.server.domain.SeasonType.SPRING, digest.getSeason());
        assertEquals(15, digest.getPrepTimeMin());
        assertEquals(0, digest.getCookTimeMin());
        assertEquals("serving", digest.getServingUnit());
        assertEquals(4, digest.getServingQty());
        assertEquals("Tuna is one of the staples in our household. \nWe eat it all the time, " +
                        "because it is simple and can be eaten for breakfast, lunch, dinner or snack.",
                digest.getIntroduction());

        assertEquals(2, digest.getInstructionGroups().size());

        for (var instructionGroup : digest.getInstructionGroups()) {
            switch (instructionGroup.getName()) {
                case "Salad" -> {
                    assertEquals(4, instructionGroup.getInstructions().size());
                    assertEquals(5, instructionGroup.getIngredients().size());
                }
                case "Dressing" -> {
                    assertEquals(0, instructionGroup.getInstructions().size());
                    assertEquals(4, instructionGroup.getIngredients().size());
                }
                default -> fail(instructionGroup.getName());
            }
        }
    }

    private FoodItems getFoodItems(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new ObjectMapper();

            return mapper.readValue(in, FoodItems.class);
        }
    }

    private RecipeDigest getRecipeDigest(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new ObjectMapper();

            return mapper.readValue(in, RecipeDigest.class);
        }
    }
}