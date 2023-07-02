package com.wildfit.server.load;

import java.io.IOException;

import com.google.common.collect.Iterables;
import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.FoodItems;
import com.wildfit.server.model.mapper.IngredientDigestMapper;
import com.wildfit.server.repository.InstructionGroupRepository;
import com.wildfit.server.repository.RecipeIngredientRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateRecipeIngredientHandler;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommonRecipe {
    protected static Long userId;

    protected static final String EMAIL = "wildfit@wildfit.prototype.com";

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RecipeRepository recipeRepository;
    @Autowired
    protected InstructionGroupRepository instructionGroupRepository;
    @Autowired
    protected RecipeIngredientRepository recipeIngredientRepository;

    @BeforeEach
    void setUp() {
        final var users = userRepository.findByEmail(EMAIL);

        userId = Iterables.getOnlyElement(users).getId();
    }

    // Use https://trackapi.nutritionix.com/v2/natural/nutrients to create JSON files
    protected FoodItems getFoodItems(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            return mapper.readValue(in, FoodItems.class);
        }
    }

    protected void addIngredient(long dbRecipeId, Long dbRecipeGroupId, FoodItemDigest foodItemDigest,
                                 Integer ingredientServingQty,
                                 String ingredientServingUnit,
                                 String description) throws UserServiceException {
        addIngredient(dbRecipeId,
                dbRecipeGroupId,
                foodItemDigest,
                ingredientServingQty.floatValue(),
                ingredientServingUnit,
                description);
    }

    protected void addIngredient(long dbRecipeId, Long dbRecipeGroupId, FoodItemDigest foodItemDigest,
                                 Float ingredientServingQty,
                                 String ingredientServingUnit,
                                 String description) throws UserServiceException {
        CreateRecipeIngredientHandler.builder()
                .withUserRepository(userRepository)
                .withRecipeRepository(recipeRepository)
                .withInstructionGroupRepository(instructionGroupRepository)
                .withRecipeIngredientRepository(recipeIngredientRepository)
                .withUserId(userId)
                .withRecipeId(dbRecipeId)
                .withRecipeGroupId(dbRecipeGroupId)
                .withRequest(IngredientDigestMapper.create(foodItemDigest,
                        description,
                        ingredientServingQty,
                        ingredientServingUnit))
                .build().execute();
    }
}
