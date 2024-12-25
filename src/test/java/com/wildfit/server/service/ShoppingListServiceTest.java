package com.wildfit.server.service;

import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ShoppingListServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    MealRepository mealRepository;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    ShoppingListRepository shoppingListRepository;

    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        shoppingListService = new ShoppingListService(userRepository, mealRepository, recipeRepository,
                                                      shoppingListRepository);
    }

    @Test
    void getShoppingList() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.getShoppingList(null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void deleteItemFromShoppingList() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.deleteItemFromShoppingList(null, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void createShoppingList_nullRequest() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.createShoppingList(null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void createShoppingList() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.createShoppingList(CreateShoppingListRequest.builder().build()));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void updateShoppingList() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> shoppingListService.updateShoppingList(null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }
}