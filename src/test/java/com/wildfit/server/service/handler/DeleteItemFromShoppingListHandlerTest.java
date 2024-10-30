package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.ShoppingListItem;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.service.ShoppingListService;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class DeleteItemFromShoppingListHandlerTest extends CommonMealHandlerTest {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ShoppingListService shoppingListService;

    @AfterEach
    void tearDown() {
        shoppingListRepository.deleteByUuid(userId);

        super.tearDown();
    }

    @Test
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

        shoppingListService.createShoppingList(CreateShoppingListRequest.builder()
                                                       .withUuid(userId)
                                                       .withMealId(mealDigest.getId()).build());

        final var shoppingList = shoppingListRepository.findByUuid(userId).orElseThrow();

        final var foodNames = List.of("pepper", "tuna in water");

        final var pepper = shoppingList.getShoppingListItems().stream().filter(x -> "pepper".equals(x.getFoodName()))
                                       .map(ShoppingListItem::getId).findFirst().orElseThrow();
        final var tuna = shoppingList.getShoppingListItems().stream()
                                     .filter(x -> "tuna in water".equals(x.getFoodName()))
                                     .map(ShoppingListItem::getId).findFirst().orElseThrow();

        final var count = shoppingList.getShoppingListItems().size();

        shoppingListService.deleteItemFromShoppingList(userId, pepper);
        shoppingListService.deleteItemFromShoppingList(userId, tuna);

        final var modifiedShoppingList = shoppingListRepository.findByUuid(userId).orElseThrow();
        final var modifiedCount = modifiedShoppingList.getShoppingListItems().size();

        assertEquals(count, modifiedCount + 2);

        final var existingNames = modifiedShoppingList.getShoppingListItems().stream()
                                                      .map(ShoppingListItem::getFoodName).toList();

        assertThat(existingNames, everyItem(not(in(foodNames))));
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> DeleteItemFromShoppingListHandler.builder().build().execute());
    }

    @Test
    void missingRequest() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> DeleteItemFromShoppingListHandler.builder()
                                                       .withUserRepository(userRepository)
                                                       .withShoppingListRepository(shoppingListRepository)
                                                       .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> DeleteItemFromShoppingListHandler.builder()
                                                       .withUserRepository(userRepository)
                                                       .withShoppingListRepository(shoppingListRepository)
                                                       .withItemId(1234L)
                                                       .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.deleteItemFromShoppingList("  ", 15L));
        assertEquals(WildfitServiceError.USER_NOT_FOUND, exception.getError());
    }
}