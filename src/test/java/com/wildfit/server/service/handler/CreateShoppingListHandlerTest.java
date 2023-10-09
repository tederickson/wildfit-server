package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CreateShoppingListHandlerTest extends CommonMealHandlerTest {

    @Autowired
    protected ShoppingListRepository shoppingListRepository;

    @AfterEach
    void tearDown() {
        if (mealDigest != null) {
            mealRepository.deleteById(mealDigest.getId());

            mealDigest = null;
        }

        super.tearDown();
    }

    @Test
    @Transactional
    void execute() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json"));

        createRecipes(recipeDigestList);

        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).collect(Collectors.toList());
        final var request = CreateMealRequest.builder()
                                             .withUuid(userId)
                                             .withRecipeIds(recipeIds).build();

        createMeal(request);

        assertNotNull(mealDigest.getId());
        assertEquals(userId, mealDigest.getUuid());

        try {
            CreateShoppingListHandler.builder()
                                     .withUserRepository(userRepository)
                                     .withRecipeRepository(recipeRepository)
                                     .withMealRepository(mealRepository)
                                     .withShoppingListRepository(shoppingListRepository)
                                     .withMealId(mealDigest.getId())
                                     .withUserId(userId)
                                     .build().execute();

            final var shoppingList = shoppingListRepository.findByUuid(userId).orElseThrow();
            System.out.println("shoppingList = " + shoppingList);
        } finally {
            shoppingListRepository.deleteByUuid(userId);
        }
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> CreateShoppingListHandler.builder().build().execute());
    }

    @Test
    void missingRequest() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> CreateShoppingListHandler.builder()
                                               .withUserRepository(userRepository)
                                               .withRecipeRepository(recipeRepository)
                                               .withMealRepository(mealRepository)
                                               .withShoppingListRepository(shoppingListRepository)
                                               .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> CreateShoppingListHandler.builder()
                                               .withUserRepository(userRepository)
                                               .withRecipeRepository(recipeRepository)
                                               .withMealRepository(mealRepository)
                                               .withShoppingListRepository(shoppingListRepository)
                                               .withMealId(1234L)
                                               .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> CreateShoppingListHandler.builder()
                                               .withUserRepository(userRepository)
                                               .withRecipeRepository(recipeRepository)
                                               .withMealRepository(mealRepository)
                                               .withShoppingListRepository(shoppingListRepository)
                                               .withMealId(15L)
                                               .withUserId("  ")
                                               .build().execute());
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }
}