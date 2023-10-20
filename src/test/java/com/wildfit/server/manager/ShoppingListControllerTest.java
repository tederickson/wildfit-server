package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingListControllerTest {
    static final Long mealId = 123L;
    static final String userId = "abra-cadabra";

    @Mock
    private ShoppingListService shoppingListService;

    private ShoppingListController controller;

    @BeforeEach
    void setUp() {
        controller = new ShoppingListController(shoppingListService);
    }

    @Test
    void getShoppingList() throws WildfitServiceException {
        assertNull(controller.getShoppingList(userId));
    }

    @Test
    void deleteItemFromShoppingList() throws WildfitServiceException {
        controller.deleteItemFromShoppingList(userId, 1319L);
    }

    @Test
    void createShoppingList() throws WildfitServiceException {
        CreateShoppingListRequest request = CreateShoppingListRequest.builder()
                                                                     .withUuid(userId)
                                                                     .withMealId(mealId)
                                                                     .build();
        assertNull(controller.createShoppingList(request));
    }

    @Test
    void updateShoppingList_mismatchedUser() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> controller.updateShoppingList(userId, ShoppingListDigest.builder().build()));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void updateShoppingList() throws WildfitServiceException {
        assertNull(controller.updateShoppingList(userId, ShoppingListDigest.builder().withUuid(userId).build()));
    }
}