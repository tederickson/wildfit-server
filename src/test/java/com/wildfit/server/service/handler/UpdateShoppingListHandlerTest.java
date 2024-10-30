package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.domain.ShoppingListItemDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UpdateShoppingListHandlerTest extends CommonMealHandlerTest {

    private static final String PEPPER = "pepper";
    private static final String APPLE = "apple";
    private static final String TUNA = "tuna in water";

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

        shoppingListRepository.findByUuid(userId).orElseThrow();

        var shoppingList = shoppingListService.getShoppingList(userId);

        final var itemListMap = shoppingList.getItems().stream()
                .collect(Collectors.groupingBy(ShoppingListItemDigest::getFoodName));

        itemListMap.forEach((k, v) -> assertEquals(1, v.size(), k));

        var foodNames = itemListMap.keySet();
        final var originalShoppingListItemCount = foodNames.size();
        final var updatedItems = List.of(PEPPER, TUNA, APPLE);

        assertTrue(foodNames.containsAll(updatedItems));

        assertTrue(shoppingList.getItems().stream().map(ShoppingListItemDigest::isPurchased)
                           .allMatch(item -> item.equals(Boolean.FALSE)));

        itemListMap.get(PEPPER).forEach(item -> item.setPurchased(true));
        itemListMap.get(TUNA).forEach(item -> item.setPurchased(true));
        itemListMap.get(APPLE).forEach(item -> item.setPurchased(true));

        shoppingListService.updateShoppingList(shoppingList);

        shoppingList = shoppingListService.getShoppingList(userId);
        assertThat(shoppingList.getItems().size(), is(originalShoppingListItemCount));

        for (var item : shoppingList.getItems()) {
            if (updatedItems.contains(item.getFoodName())) {
                assertTrue(item.isPurchased());
            }
            else {
                assertFalse(item.isPurchased());
            }
        }
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> UpdateShoppingListHandler.builder().build().execute());
    }

    @Test
    void missingRequest() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> UpdateShoppingListHandler.builder()
                                                   .withShoppingListRepository(shoppingListRepository)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.updateShoppingList(ShoppingListDigest.builder()
                                                                                                .build()));
        assertEquals(WildfitServiceError.SHOPPING_LIST_NOT_FOUND, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.updateShoppingList(ShoppingListDigest.builder()
                                                                                                .withUuid("  ")
                                                                                                .build()));

        assertEquals(WildfitServiceError.SHOPPING_LIST_NOT_FOUND, exception.getError());
    }
}