package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import com.wildfit.server.domain.InstructionDigest;
import com.wildfit.server.domain.PhotoDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.InstructionRepository;
import com.wildfit.server.repository.RecipeIngredientRepository;
import com.wildfit.server.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class AbstractRecipeHandlerTest extends AbstractHandlerTest {
    static final InstructionDigest step1 = InstructionDigest.builder().withStepNumber(1)
            .withInstruction("Heat the oil in a heavy frying pan over medium-high heat, then cook the beef " +
                    "until it’s " +
                    "cooked through and starting to brown, breaking apart with a turner as it cooks.").build();
    static final InstructionDigest step2 = InstructionDigest.builder().withStepNumber(2)
            .withInstruction("While beef cooks, mix together the fish sauce, chili sauce, and water in a " +
                    "small bowl. Zest the skin " +
                    "of the lime and squeeze the juice. (You may need two limes to get enough juice, " +
                    "but only use " +
                    "the zest from one lime.) Thinly slice the green onions and chop the cilantro. " +
                    "Most iceberg lettuce does not need washing as long as you remove the outer leaves. " +
                    "Cut out the core and cut the lettuce into quarters to make 'cups' to hold the beef " +
                    "mixture.").build();
    static final InstructionDigest step3 = InstructionDigest.builder().withStepNumber(3)
            .withInstruction("When the beef is done, add the chili sauce mixture and let it sizzle until " +
                    "the water has evaporated, " +
                    "stirring a few times to get the flavor mixed through the meat. Turn off the heat and " +
                    "stir in " +
                    "the lime zest, lime juice, sliced green onions, and chopped cilantro.").build();
    static final InstructionDigest step4 = InstructionDigest.builder().withStepNumber(4)
            .withInstruction("Serve meat mixture with iceberg lettuce leaves. Fill with beef and wrap " +
                    "around it. Eaten with your hands.").build();

    static final String INTRODUCTION = "These lettuce wraps are so easy and full of flavor! " +
            "They make a great side dish, or are perfect for a healthy spring approved recipe.";

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

    protected static Long userId;
    protected static RecipeDigest testRecipe;

    @Autowired
    protected RecipeRepository recipeRepository;
    @Autowired
    protected InstructionGroupRepository instructionGroupRepository;
    @Autowired
    protected InstructionRepository instructionRepository;
    @Autowired
    protected RecipeIngredientRepository recipeIngredientRepository;

    @BeforeEach
    void setUp() {
        final var users = userRepository.findByEmail(EMAIL);

        assertTrue(CollectionUtils.isEmpty(users));

        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(LocalDate.now())
                .withPassword("encoded password")
                .withEmail(EMAIL).build();
        final var dbUser = userRepository.save(user);

        userId = dbUser.getId();
    }

    @AfterEach
    void tearDown() {
        if (testRecipe != null) {
            recipeRepository.deleteById(testRecipe.getId());

            final var instructionGroups = instructionGroupRepository.findByRecipeId(testRecipe.getId());
            instructionGroupRepository.deleteAll(instructionGroups);

            final var ingredients = recipeIngredientRepository.findByRecipeId(testRecipe.getId());
            recipeIngredientRepository.deleteAll(ingredients);

            testRecipe = null;
        }

        super.tearDown();
    }

    protected void createRecipe(RecipeDigest recipe) throws UserServiceException {
        testRecipe = CreateRecipeHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withUserId(userId)
                .withRequest(recipe)
                .build().execute();
    }
}
