package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.ShoppingListItemDigest;
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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class GetShoppingListHandlerTest extends CommonMealHandlerTest {

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

        final var dbShoppingList = shoppingListRepository.findByUuid(userId).orElseThrow();
        final var shoppingList = shoppingListService.getShoppingList(userId);

        assertEquals(dbShoppingList.getId(), shoppingList.id());
        assertEquals(dbShoppingList.getUuid(), shoppingList.uuid());

        final var dbItemMap = dbShoppingList.getShoppingListItems().stream()
                .collect(Collectors.groupingBy(ShoppingListItem::getFoodName));
        final var itemMap = shoppingList.items().stream()
                .collect(Collectors.groupingBy(ShoppingListItemDigest::foodName));

        assertThat(dbItemMap.keySet(), containsInAnyOrder(itemMap.keySet().toArray()));
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> GetShoppingListHandler.builder().build().execute());
    }


    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> GetShoppingListHandler.builder()
                                                   .withShoppingListRepository(shoppingListRepository)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void noShoppingList() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.getShoppingList("  "));
        assertEquals(WildfitServiceError.SHOPPING_LIST_NOT_FOUND, exception.getError());
    }
}