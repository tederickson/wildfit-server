package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.ShoppingListItemDigest;
import com.wildfit.server.model.Ingredient;
import com.wildfit.server.model.ShoppingListItem;
import org.apache.commons.lang3.StringUtils;

public class ShoppingListItemMapper {
    private ShoppingListItemMapper() {
    }

    public static ShoppingListItem map(Ingredient ingredient) {
        return new ShoppingListItem()
                .setFoodName(StringUtils.trimToNull(ingredient.getFoodName()))
                .setServingQty(ingredient.getIngredientServingQty())
                .setServingUnit(StringUtils.trimToNull(ingredient.getIngredientServingUnit()))
                .setIngredientType(ingredient.getIngredientType())
                .setPurchased(false);
    }

    public static ShoppingListItemDigest map(ShoppingListItem shoppingListItem) {
        return ShoppingListItemDigest.builder()
                                     .withId(shoppingListItem.getId())
                                     .withFoodName(shoppingListItem.getFoodName())
                                     .withTotalQuantity(shoppingListItem.getServingQty())
                                     .withUnit(shoppingListItem.getServingUnit())
                                     .withIngredientType(shoppingListItem.getIngredientType())
                                     .withPurchased(shoppingListItem.isPurchased())
                                     .build();
    }
}
