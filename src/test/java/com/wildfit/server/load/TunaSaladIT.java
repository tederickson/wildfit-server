package com.wildfit.server.load;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.IngredientType;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import com.wildfit.server.service.handler.CreateRecipeHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class TunaSaladIT extends CommonRecipe {

    public static final InstructionDigest STEP_1 = InstructionDigest.builder().withStepNumber(1)
                                                                    .withInstruction(
                                                                            "Put the tuna in a bowl and break it up with a fork.")
                                                                    .build();
    public static final InstructionDigest STEP_3 = InstructionDigest.builder().withStepNumber(3)
                                                                    .withInstruction(
                                                                            "Add the finely chopped onion and the capers. " +
                                                                                    "Mix it together with the dressing ingredients and done.")
                                                                    .build();
    public static final InstructionDigest STEP_4 = InstructionDigest.builder().withStepNumber(4)
                                                                    .withInstruction(
                                                                            "You can add any fresh herb you like. Dill goes especially well with tuna.")
                                                                    .build();

    @Test
    void tunaSaladWithAppleAndCelerySummer() throws UserServiceException, IOException {
        final var season = Season.SUMMER;
        final var name = "Tuna salad with apple and celery (Summer)";
        final var exists = recipeRepository.findAllBySeasonAndName(season.getCode(), name, PageRequest.of(0, 10));

        if (exists.isEmpty()) {
            final var step2 = InstructionDigest.builder().withStepNumber(2)
                                               .withInstruction(
                                                       "Slice the celery and the apples to the same thickness. Chop the pickles small. ")
                                               .build();


            final var instructionGroup1 = InstructionGroupDigest.builder()
                                                                .withInstructionGroupNumber(1)
                                                                .withName("Dressing").build();
            final var instructionGroup2 = InstructionGroupDigest.builder()
                                                                .withInstructionGroupNumber(2)
                                                                .withName("Salad")
                                                                .withInstructions(
                                                                        List.of(STEP_1, step2, STEP_3, STEP_4)).build();
            final var recipe = RecipeDigest.builder()
                                           .withName(name)
                                           .withSeason(season.toSeasonType())
                                           .withIntroduction(
                                                   "Tuna is one of the staples in our household. We eat it all the time, because " +
                                                           "it is simple and can be eaten for breakfast, lunch, dinner and snack. ")
                                           .withPrepTimeMin(15)
                                           .withCookTimeMin(0)
                                           .withServingQty(4)
                                           .withServingUnit("serving")
                                           .withInstructionGroups(List.of(instructionGroup1, instructionGroup2))
                                           .build();
            final var response = CreateRecipeHandler.builder()
                                                    .withUserRepository(userRepository)
                                                    .withRecipeRepository(recipeRepository)
                                                    .withInstructionGroupRepository(instructionGroupRepository)
                                                    .withUserId(UUID)
                                                    .withRequest(recipe)
                                                    .build().execute();
            assertNotNull(response);

            final var dbRecipeId = response.getId();

            for (var dbRecipeGroup : response.getInstructionGroups()) {
                switch (dbRecipeGroup.getInstructionGroupNumber()) {
                    case 1 -> dressingIngredients(dbRecipeId, dbRecipeGroup.getId());
                    case 2 -> saladIngredients(dbRecipeId, dbRecipeGroup.getId(), season.toSeasonType());
                    default -> fail("not expecting step " + dbRecipeGroup);
                }
            }
        }
    }

    @Test
    void tunaSaladWithAppleAndCelerySpring() throws UserServiceException, IOException {
        final var season = Season.SPRING;
        final var name = "Tuna salad";
        final var exists = recipeRepository.findAllBySeasonAndName(season.getCode(), name, PageRequest.of(0, 10));

        if (exists.isEmpty()) {
            final var step2 = InstructionDigest.builder().withStepNumber(2)
                                               .withInstruction(
                                                       "Dice the pickles and the celery to the same thickness.")
                                               .build();

            final var instructionGroup1 = InstructionGroupDigest.builder()
                                                                .withInstructionGroupNumber(1)
                                                                .withName("Dressing").build();
            final var instructionGroup2 = InstructionGroupDigest.builder()
                                                                .withInstructionGroupNumber(2)
                                                                .withName("Salad")
                                                                .withInstructions(
                                                                        List.of(STEP_1, step2, STEP_3, STEP_4)).build();
            final var recipe = RecipeDigest.builder()
                                           .withName(name)
                                           .withSeason(season.toSeasonType())
                                           .withIntroduction(
                                                   "Tuna is one of the staples in our household. We eat it all the time, because " +
                                                           "it is simple and can be eaten for breakfast, lunch, dinner and snack. ")
                                           .withPrepTimeMin(15)
                                           .withCookTimeMin(0)
                                           .withServingQty(4)
                                           .withServingUnit("serving")
                                           .withInstructionGroups(List.of(instructionGroup1, instructionGroup2))
                                           .build();
            final var response = CreateRecipeHandler.builder()
                                                    .withUserRepository(userRepository)
                                                    .withRecipeRepository(recipeRepository)
                                                    .withInstructionGroupRepository(instructionGroupRepository)
                                                    .withUserId(UUID)
                                                    .withRequest(recipe)
                                                    .build().execute();
            assertNotNull(response);

            final var dbRecipeId = response.getId();

            for (var dbRecipeGroup : response.getInstructionGroups()) {
                switch (dbRecipeGroup.getInstructionGroupNumber()) {
                    case 1 -> dressingIngredients(dbRecipeId, dbRecipeGroup.getId());
                    case 2 -> saladIngredients(dbRecipeId, dbRecipeGroup.getId(), season.toSeasonType());
                    default -> fail("not expecting step " + dbRecipeGroup);
                }
            }
        }
    }

    private void dressingIngredients(long dbRecipeId, long dbRecipeGroupId) throws IOException, UserServiceException {
        FoodItems foodItems;
        FoodItemDigest foodItemDigest;

        foodItems = getFoodItems("load/mayo.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1, "tbsp",
                "1 tbsp sugar-free mayonnaise", null);

        foodItems = getFoodItems("load/greek_yoghurt.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1, "tbsp",
                "1 tbsp Greek yoghurt", IngredientType.DAIRY);

        foodItems = getFoodItems("load/salt.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 0.125f, "tsp",
                "1/8 teaspoon salt (adjust to your preference)", IngredientType.SPICE);

        foodItems = getFoodItems("load/pepper.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 0.125f, "tsp",
                "1/8 teaspoon pepper (adjust to your preference)", IngredientType.SPICE);
    }

    private void saladIngredients(long dbRecipeId, long dbRecipeGroupId, SeasonType season)
            throws IOException, UserServiceException {
        var foodItems = getFoodItems("load/tuna_in_water.json");
        var foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 2, "can",
                "2 cans of tuna in water, drained", null);

        if (SeasonType.SUMMER.equals(season)) {
            foodItems = getFoodItems("load/apple.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 0.5f, "apple",
                    "1/2 apple", IngredientType.PRODUCE);
        }

        foodItems = getFoodItems("load/celery.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1, "stalk",
                "1 stalk celery", IngredientType.PRODUCE);

        foodItems = getFoodItems("load/capers.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1, "tbsp",
                "1 tbsp capers", null);

        foodItems = getFoodItems("load/red_onion.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1, "onion",
                "1 spring onion or a small red onion, finely chopped", IngredientType.PRODUCE);

        foodItems = getFoodItems("load/cornichon.json");
        foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
        addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 4, "cornichon",
                "4 cornichon (small sugar free pickled cucumbers)", null);
    }

}