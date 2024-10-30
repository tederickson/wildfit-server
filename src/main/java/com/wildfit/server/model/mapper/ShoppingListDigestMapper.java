package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.ShoppingListDigest;
import com.wildfit.server.model.ShoppingList;

public class ShoppingListDigestMapper {
    private ShoppingListDigestMapper() {
    }

    public static ShoppingListDigest map(ShoppingList shoppingList) {
        return ShoppingListDigest.builder()
                .withId(shoppingList.getId())
                .withUuid(shoppingList.getUuid())
                .withItems(shoppingList.getShoppingListItems().stream()
                                   .map(ShoppingListItemMapper::map)
                                   .toList())
                .build();

    }
}
