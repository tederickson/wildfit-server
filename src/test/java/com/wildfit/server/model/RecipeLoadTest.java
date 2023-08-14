package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;

import com.wildfit.server.domain.IngredientType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeLoadTest {

    static final String EMAIL = "tederickson35@gmail.com";
    static final String recipeName = "Chili Beef Lettuce Wraps";
    static final String introduction = "These lettuce wraps are so easy and full of flavor!";
    static final int prepTimeMin = 5;
    static final int cookTimeMin = 15;
    static final String servingUnit = "serving";
    static final int servingQty = 4;
    static final LocalDateTime created = LocalDateTime.now().minusHours(1);
    static final LocalDateTime updated = LocalDateTime.now();

    static Long recipeId = null;
    @Autowired
    com.wildfit.server.repository.RecipeRepository recipeRepository;

    public static Instruction BEEF_STEP_1() {
        return new Instruction()
                .setStepNumber(1)
                .setText("Heat the oil in a heavy frying pan over medium-high heat, then cook the beef " +
                        "until it’s " +
                        "cooked through and starting to brown, breaking apart with a turner as it cooks.");
    }

    public static Instruction BEEF_STEP_2() {
        return new Instruction()
                .setStepNumber(2)
                .setText("While beef cooks, mix together the fish sauce, chili sauce, and water in a " +
                        "small bowl. Zest the skin of the lime and squeeze the juice. " +
                        "(You may need two limes to get enough juice, but only use the zest from one lime.) " +
                        "Thinly slice the green onions and chop the cilantro. " +
                        "Most iceberg lettuce does not need washing as long as you remove the outer leaves. " +
                        "Cut out the core and cut the lettuce into quarters to make 'cups' to hold the beef " +
                        "mixture.");
    }

    public static Instruction BEEF_STEP_3() {
        return new Instruction()
                .setStepNumber(3)
                .setText("When the beef is done, add the chili sauce mixture and let it sizzle until " +
                        "the water has evaporated, " +
                        "stirring a few times to get the flavor mixed through the meat. Turn off the heat and " +
                        "stir in " +
                        "the lime zest, lime juice, sliced green onions, and chopped cilantro.");
    }

    public static Instruction BEEF_STEP_4() {
        return new Instruction()
                .setStepNumber(4)
                .setText("Serve meat mixture with iceberg lettuce leaves. Fill with beef and wrap " +
                        "around it. Eaten with your hands.");
    }

    private static Recipe buildMinimalRecipe() {
        return new Recipe()
                .setSeason(Season.SUMMER)
                .setName(recipeName)
                .setEmail(EMAIL)
                .setIntroduction(introduction)
                .setPrepTimeMin(prepTimeMin)
                .setCookTimeMin(cookTimeMin)
                .setServingUnit(servingUnit)
                .setServingQty(servingQty)
                .setCreated(created)
                .setUpdated(updated);
    }

    @AfterEach
    void tearDown() {
        if (recipeId != null) {
            recipeRepository.deleteById(recipeId);
        }
        recipeId = null;
    }

    @Test
    void minimalRecipe() {
        final var recipe = saveRecipe(buildMinimalRecipe());

        assertEquals(introduction, recipe.getIntroduction());
    }

    @Test
    void addRecipeGroup() {
        final var recipe = buildMinimalRecipe();

        final RecipeGroup dressing = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Dressing")
                .setRecipeGroupNumber(1);
        final RecipeGroup salad = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Salad")
                .setRecipeGroupNumber(2);
        final RecipeGroup beef = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Chili Beef")
                .setRecipeGroupNumber(3);

        recipe.setRecipeGroups(List.of(beef, salad, dressing));

        var dbRecipe = saveRecipe(recipe);
        assertEquals(3, dbRecipe.getRecipeGroups().size());

        for (var recipeGroup : dbRecipe.getRecipeGroups()) {
            switch (recipeGroup.getRecipeGroupNumber()) {
                case 1 -> assertEquals("Dressing", recipeGroup.getName());
                case 2 -> assertEquals("Salad", recipeGroup.getName());
                case 3 -> assertEquals("Chili Beef", recipeGroup.getName());
                default -> fail("Unknown group number " + recipeGroup.getRecipeGroupNumber());
            }
        }
    }

    @Test
    void addInstructions() {
        final var recipe = buildMinimalRecipe();

        final RecipeGroup dressing = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Dressing")
                .setRecipeGroupNumber(1);
        final RecipeGroup salad = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Salad")
                .setRecipeGroupNumber(2);
        final RecipeGroup beef = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Chili Beef")
                .setRecipeGroupNumber(3);
        recipe.setRecipeGroups(List.of(beef, salad, dressing));


        beef.setCommonRecipes(List.of(BEEF_STEP_1(), BEEF_STEP_2(), BEEF_STEP_3(), BEEF_STEP_4()));

        saveRecipe(recipe);
    }

    @Test
    void addInstructionsSeveralTimes() {
        for (int i = 0; i < 4; i++) {
            final var recipe = buildMinimalRecipe();

            final RecipeGroup dressing = new RecipeGroup()
                    .setRecipe(recipe)
                    .setName("Dressing")
                    .setRecipeGroupNumber(1);
            final RecipeGroup salad = new RecipeGroup()
                    .setRecipe(recipe)
                    .setName("Salad")
                    .setRecipeGroupNumber(2);
            final RecipeGroup beef = new RecipeGroup()
                    .setRecipe(recipe)
                    .setName("Chili Beef")
                    .setRecipeGroupNumber(3);
            recipe.setRecipeGroups(List.of(beef, salad, dressing));

            beef.setCommonRecipes(List.of(BEEF_STEP_1(), BEEF_STEP_2(), BEEF_STEP_3(), BEEF_STEP_4()));

            saveRecipe(recipe);
        }
    }

    @Test
    void addInstructionsAndIngredients() {
        final var recipe = buildMinimalRecipe();

        final RecipeGroup dressing = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Dressing")
                .setRecipeGroupNumber(1);
        recipe.add(dressing);

        final RecipeGroup salad = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Salad")
                .setRecipeGroupNumber(2);
        recipe.add(salad);

        final RecipeGroup beef = new RecipeGroup()
                .setRecipe(recipe)
                .setName("Chili Beef")
                .setRecipeGroupNumber(3);
        recipe.add(beef);

        beef.setCommonRecipes(List.of(BEEF_STEP_1(), BEEF_STEP_2(), BEEF_STEP_3(), BEEF_STEP_4()));

        final Ingredient MAYO = new Ingredient()
                .setFoodName("sugar-free mayonnaise")
                .setDescription("1 tbsp sugar-free mayonnaise")
                .setIngredientServingQty(1f)
                .setIngredientServingUnit("tbsp")
                .setIngredientType(IngredientType.NONE);
        final Ingredient PLAIN_YOGURT = new Ingredient()
                .setFoodName("plain Greek yogurt")
                .setDescription("1 tbsp Greek yoghurt")
                .setIngredientServingQty(1f)
                .setIngredientServingUnit("tbsp")
                .setIngredientType(IngredientType.DAIRY);
        final Ingredient SALT = new Ingredient()
                .setFoodName("salt")
                .setDescription("1/16 teaspoon salt (adjust to your preference)")
                .setIngredientServingQty(0.125f)
                .setIngredientServingUnit("tsp")
                .setIngredientType(IngredientType.SPICE);
        final Ingredient PEPPER = new Ingredient()
                .setFoodName("pepper")
                .setDescription("1/16 teaspoon pepper (adjust to your preference)")
                .setIngredientServingQty(0.125f)
                .setIngredientServingUnit("tsp")
                .setIngredientType(IngredientType.SPICE);

        Instruction mix = new Instruction().setStepNumber(1).setText("Mix all ingredients.");
        List.of(MAYO, PLAIN_YOGURT, mix, SALT, PEPPER).forEach(dressing::add);


        salad.add(new Instruction()
                .setStepNumber(1)
                .setText("Put the tuna in a bowl and break it up with a fork."));
        salad.add(new Instruction()
                .setStepNumber(2)
                .setText("Slice the celery and the apples to the same thickness. Chop the pickles small."));
        salad.add(new Instruction()
                .setStepNumber(3)
                .setText("Add the finely chopped onion and the capers. \n" +
                        "Mix it together with the dressing ingredients and done."));
        salad.add(new Instruction()
                .setStepNumber(4)
                .setText("You can add any fresh herb you like. Dill goes especially well with tuna."));

        salad.add(new Ingredient().setFoodName("tuna in water")
                                  .setDescription("2 cans of tuna in water, drained")
                                  .setIngredientServingQty(2f)
                                  .setIngredientServingUnit("can")
                                  .setIngredientType(IngredientType.NONE));
        salad.add(new Ingredient().setFoodName("apple")
                                  .setDescription("1/2 apple")
                                  .setIngredientServingQty(0.5f)
                                  .setIngredientServingUnit("apple")
                                  .setIngredientType(IngredientType.PRODUCE));
        salad.add(new Ingredient().setFoodName("celery")
                                  .setDescription("1 stalk celery")
                                  .setIngredientServingQty(1f)
                                  .setIngredientServingUnit("stalk")
                                  .setIngredientType(IngredientType.PRODUCE));
        salad.add(new Ingredient().setFoodName("capers")
                                  .setDescription("1 tbsp capers")
                                  .setIngredientServingQty(1f)
                                  .setIngredientServingUnit("tbsp")
                                  .setIngredientType(IngredientType.NONE));
        salad.add(new Ingredient().setFoodName("red onion")
                                  .setDescription("1 small red onion or spring onion")
                                  .setIngredientServingQty(1f)
                                  .setIngredientServingUnit("onion")
                                  .setIngredientType(IngredientType.PRODUCE));
        salad.add(new Ingredient().setFoodName("cornichon")
                                  .setDescription("4 cornichon (small sugar free pickled cucumbers)")
                                  .setIngredientServingQty(4f)
                                  .setIngredientServingUnit("cornichon")
                                  .setIngredientType(IngredientType.NONE));

        saveRecipe(recipe);
    }

    private Recipe saveRecipe(Recipe recipe) {
        recipe.assignAllParents();

        final var dbRecipe = recipeRepository.save(recipe);
        recipeId = dbRecipe.getId();

        return dbRecipe;
    }
}
