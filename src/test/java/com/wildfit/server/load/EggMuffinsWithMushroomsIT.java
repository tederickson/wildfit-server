package com.wildfit.server.load;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.IngredientType;
import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.Season;
import com.wildfit.server.model.mapper.FoodItemDigestMapper;
import com.wildfit.server.service.handler.CreateRecipeHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class EggMuffinsWithMushroomsIT extends CommonRecipe {
    @Test
    void spring() throws UserServiceException, IOException {
        final var season = Season.SPRING;
        final var name = "Egg muffins with mushrooms and herbs";
        final var exists = recipeRepository.findAllBySeasonAndName(season.getCode(), name, PageRequest.of(0, 10));

        if (exists.isEmpty()) {
            final var step1 = InstructionDigest.builder().withStepNumber(1)
                    .withInstruction("Preheat the oven to 350F.").build();
            final var step2 = InstructionDigest.builder().withStepNumber(2)
                    .withInstruction("Sauté the bacon bits with the onion if you are using red or yellow onion.").build();
            final var step3 = InstructionDigest.builder().withStepNumber(3)
                    .withInstruction("Whisk the eggs and add all the ingredients, but save a few herbs to decorate.").build();
            final var step4 = InstructionDigest.builder().withStepNumber(4)
                    .withInstruction("Grease the muffin tin and pour in the mixture to fill up to 2/3 " +
                            "(they will raise a bit).").build();
            final var step5 = InstructionDigest.builder().withStepNumber(5)
                    .withInstruction("Bake for about 25 mins. Check at 20 mins. I like my eggs muffins soft, " +
                            "but if you are batch cooking, they need to cook through.").build();
            final var instructionGroup1 = InstructionGroupDigest.builder()
                    .withInstructionGroupNumber(2)
                    .withInstructions(List.of(step1, step2, step3, step4, step5)).build();
            final var recipe = RecipeDigest.builder()
                    .withName(name)
                    .withSeason(season.toSeasonType())
                    .withIntroduction("Perfect for batch cooking, breakfast, lunch, dinner and snack. " +
                            "High in protein, fat and nutrients. Low in carbs.")
                    .withPrepTimeMin(15)
                    .withCookTimeMin(25)
                    .withServingQty(6)
                    .withServingUnit("serving")
                    .withInstructionGroups(List.of(instructionGroup1))
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
            final var dbRecipeGroupId = Iterables.getOnlyElement(response.getInstructionGroups()).getId();

            var foodItems = getFoodItems("load/egg.json");
            var foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 8, "large",
                    "8 large eggs", IngredientType.DAIRY);

            foodItems = getFoodItems("load/bacon_bits.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 100, "g",
                    "100g bacon bits (or more)", IngredientType.NONE);

            foodItems = getFoodItems("load/red_onion.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 0.5f, "medium",
                    "2-3 spring onions or 1/2 onion, chopped", IngredientType.PRODUCE);

            foodItems = getFoodItems("load/mushroom.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 100, "g",
                    "100g mushrooms, diced", IngredientType.PRODUCE);

            foodItems = getFoodItems("load/thyme.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 6, "sprig",
                    "green herb sprigs (parsley, dill, coriander, basil, thyme, chives are my go-to herbs)",
                    IngredientType.PRODUCE);

            foodItems = getFoodItems("load/salt.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 0.125f, "tsp",
                    "1/8 teaspoon salt (adjust to your preference)",
                    IngredientType.SPICE);

            foodItems = getFoodItems("load/pepper.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 0.125f, "tsp",
                    "1/8 teaspoon pepper (adjust to your preference)", IngredientType.SPICE);

            foodItems = getFoodItems("load/butter.json");
            foodItemDigest = FoodItemDigestMapper.map(foodItems.getFoods()[0]);
            addIngredient(dbRecipeId, dbRecipeGroupId, foodItemDigest, 1, "tbsp",
                    "1 tablespoon of butter or olive oil to grease your muffin tin", IngredientType.DAIRY);
        }
    }
}
