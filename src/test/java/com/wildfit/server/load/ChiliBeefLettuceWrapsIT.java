package com.wildfit.server.load;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import com.wildfit.server.service.handler.CreateRecipeHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class ChiliBeefLettuceWrapsIT extends CommonRecipe {


    @Test
    void chiliBeefLettuceWraps() throws UserServiceException, IOException {
        final var season = SeasonType.SPRING;
        final var name = "Chili Beef Lettuce Wraps";
        final var exists = recipeRepository.findAllBySeasonAndName(season.getCode(), name, PageRequest.of(0, 10));

        if (exists.isEmpty()) {
            final var step1 = InstructionDigest.builder().withStepNumber(1)
                    .withInstruction("Heat the oil in a heavy frying pan over medium-high heat, then cook the beef " +
                            "until it’s " +
                            "cooked through and starting to brown, breaking apart with a turner as it cooks.").build();
            final var step2 = InstructionDigest.builder().withStepNumber(2)
                    .withInstruction("While beef cooks, mix together the fish sauce, chili sauce, and water in a " +
                            "small bowl. Zest the skin " +
                            "of the lime and squeeze the juice. (You may need two limes to get enough juice, " +
                            "but only use " +
                            "the zest from one lime.) Thinly slice the green onions and chop the cilantro. " +
                            "Most iceberg lettuce does not need washing as long as you remove the outer leaves. " +
                            "Cut out the core and cut the lettuce into quarters to make 'cups' to hold the beef " +
                            "mixture.").build();
            final var step3 = InstructionDigest.builder().withStepNumber(3)
                    .withInstruction("When the beef is done, add the chili sauce mixture and let it sizzle until " +
                            "the water has evaporated, " +
                            "stirring a few times to get the flavor mixed through the meat. Turn off the heat and " +
                            "stir in " +
                            "the lime zest, lime juice, sliced green onions, and chopped cilantro.").build();
            final var step4 = InstructionDigest.builder().withStepNumber(4)
                    .withInstruction("Serve meat mixture with iceberg lettuce leaves. Fill with beef and wrap " +
                            "around it. Eaten with your hands.").build();

            final var instructionGroup = InstructionGroupDigest.builder()
                    .withInstructionGroupNumber(1)
                    .withInstructions(List.of(step1, step2, step3, step4)).build();
            final var recipe = RecipeDigest.builder()
                    .withName(name)
                    .withSeason(season)
                    .withIntroduction("These lettuce wraps are so easy and full of flavor! " +
                            "They make a great side dish, or are perfect for a healthy spring approved recipe.")
                    .withPrepTimeMin(5)
                    .withCookTimeMin(15)
                    .withServingQty(4)
                    .withServingUnit("serving")
                    .withInstructionGroups(java.util.List.of(instructionGroup))
                    .build();
            final var response = CreateRecipeHandler.builder()
                    .withUserRepository(userRepository)
                    .withRecipeRepository(recipeRepository)
                    .withInstructionGroupRepository(instructionGroupRepository)
                    .withUserId(userId)
                    .withRequest(recipe)
                    .build().execute();
            assertNotNull(response);

            final var dbRecipeId = response.getId();
            final var dbRecipeGroupId = Iterables.getOnlyElement(response.getInstructionGroups()).getId();
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

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 2f, "tsp");

            foodItems = getFoodItems("load/lean_ground_beef.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "lb");

            foodItems = getFoodItems("load/fish_sauce.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "tbsp");

            foodItems = getFoodItems("load/chili_sauce.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 2f, "tbsp");

            foodItems = getFoodItems("load/water.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 2f, "tbsp");

            foodItems = getFoodItems("load/lime_zest.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "large");

            foodItems = getFoodItems("load/lime_juice.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "tbsp");

            foodItems = getFoodItems("load/green_onion.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "cup");

            foodItems = getFoodItems("load/cilantro.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "cup");

            foodItems = getFoodItems("load/iceberg_lettuce.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);

            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1f, "cup");
        }
    }
}