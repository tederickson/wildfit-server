package com.wildfit.server.service;

import com.wildfit.server.domain.CreateShoppingListRequest;
import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.exception.WildfitServiceException;

public interface ShoppingListService {
    ShoppingListDigest getShoppingList(String userId) throws WildfitServiceException;

    void deleteItemFromShoppingList(String userId, Long shoppingListId) throws WildfitServiceException;

    void createShoppingList(CreateShoppingListRequest request) throws WildfitServiceException;
}