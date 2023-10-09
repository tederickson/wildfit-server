package com.wildfit.server.model.mapper;

import com.wildfit.server.model.Ingredient;
import com.wildfit.server.model.ShoppingListItem;
import org.apache.commons.lang3.StringUtils;

public class ShoppingListMapper {
    private ShoppingListMapper() {
    }

    public static ShoppingListItem map(Ingredient ingredient) {
        return new ShoppingListItem()
                .setFoodName(StringUtils.trimToNull(ingredient.getFoodName()))
                .setServingQty(ingredient.getIngredientServingQty())
                .setServingUnit(StringUtils.trimToNull(ingredient.getIngredientServingUnit()))
                .setIngredientType(ingredient.getIngredientType());
    }
}
