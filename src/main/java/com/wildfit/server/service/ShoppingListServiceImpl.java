package com.wildfit.server.service;

import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.service.handler.CreateShoppingListHandler;
import com.wildfit.server.service.handler.DeleteItemFromShoppingListHandler;
import com.wildfit.server.service.handler.GetShoppingListHandler;
import com.wildfit.server.service.handler.UpdateShoppingListHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, a lightweight service, separation of concerns, and allows several people to work on
 * the same service without merge collisions.
 */
@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;
    private final ShoppingListRepository shoppingListRepository;

    @Override
    public ShoppingListDigest getShoppingList(String userId) throws WildfitServiceException {
        return GetShoppingListHandler.builder()
                                     .withShoppingListRepository(shoppingListRepository)
                                     .withUserId(userId)
                                     .build().execute();
    }

    @Override
    public void deleteItemFromShoppingList(String userId, Long itemId) throws WildfitServiceException {
        DeleteItemFromShoppingListHandler.builder()
                                         .withUserRepository(userRepository)
                                         .withShoppingListRepository(shoppingListRepository)
                                         .withUserId(userId)
                                         .withItemId(itemId)
                                         .build().execute();
    }

    @Override
    public void createShoppingList(CreateShoppingListRequest request) throws WildfitServiceException {
        CreateShoppingListHandler.builder()
                                 .withUserRepository(userRepository)
                                 .withRecipeRepository(recipeRepository)
                                 .withMealRepository(mealRepository)
                                 .withShoppingListRepository(shoppingListRepository)
                                 .withMealId(request.getMealId())
                                 .withUserId(request.getUuid())
                                 .build()
                                 .execute();
    }

    @Override
    public void updateShoppingList(ShoppingListDigest request) throws WildfitServiceException {
        UpdateShoppingListHandler.builder()
                                 .withShoppingListRepository(shoppingListRepository)
                                 .withRequest(request)
                                 .build().execute();

    }
}
