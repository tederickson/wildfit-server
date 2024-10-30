package com.wildfit.server.service.handler;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.CommonRecipeType;
import com.wildfit.server.model.Ingredient;
import com.wildfit.server.model.MealSummary;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.ShoppingList;
import com.wildfit.server.model.ShoppingListItem;
import com.wildfit.server.model.mapper.ShoppingListItemMapper;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

@Builder(setterPrefix = "with")
public class CreateShoppingListHandler {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;
    private final ShoppingListRepository shoppingListRepository;

    private final String userId;
    private Long mealId;

    public void execute() throws WildfitServiceException {
        validate();

        userRepository.findByUuid(userId)
                .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND));

        final var meal = mealRepository.findById(mealId).orElseThrow(
                () -> new WildfitServiceException(WildfitServiceError.MEAL_NOT_FOUND));

        shoppingListRepository.deleteByUuid(userId);

        final var shoppingList = shoppingListRepository.save(new ShoppingList().setUuid(userId));

        Map<String, List<ShoppingListItem>> shoppingListItemMap = new HashMap<>();

        for (var recipeId : meal.getRecipes().stream().map(MealSummary::getRecipeId).toList()) {
            for (var recipeGroup : getRecipe(recipeId).getRecipeGroups()) {
                var shoppingListItems = recipeGroup.getCommonRecipes().stream()
                        .filter(x -> CommonRecipeType.INGREDIENT.equals(x.getType()))
                        .map(x -> (Ingredient) x)
                        .map(ShoppingListItemMapper::map)
                        .toList();

                for (var item : shoppingListItems) {
                    final var key = item.getFoodName();
                    List<ShoppingListItem> existing = shoppingListItemMap.get(key);

                    if (existing == null) {
                        existing = new ArrayList<>();
                    }
                    existing.add(item);

                    shoppingListItemMap.put(key, existing);
                }
            }
        }

        List<ShoppingListItem> shoppingListItems = new ArrayList<>();

        shoppingListItemMap.forEach((k, v) -> combineIngredients(v));
        shoppingListItemMap.forEach((k, v) -> shoppingListItems.addAll(v));

        shoppingList.setShoppingListItems(shoppingListItems);
        shoppingList.updateShoppingListItems();

        shoppingListRepository.save(shoppingList);
    }

    private void combineIngredients(List<ShoppingListItem> shoppingListItems) {
        if (shoppingListItems.size() > 1) {
            ListIterator<ShoppingListItem> itr = shoppingListItems.listIterator();

            var first = itr.next();
            while (itr.hasNext()) {
                var next = itr.next();

                if (first.getServingUnit().equals(next.getServingUnit())) {
                    itr.remove();

                    first.setServingQty(first.getServingQty() + next.getServingQty());
                }
            }
        }
    }

    private Recipe getRecipe(Long recipeId) throws WildfitServiceException {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.RECIPE_NOT_FOUND));

    }

    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(mealRepository, "mealRepository");
        Objects.requireNonNull(recipeRepository, "recipeRepository");
        Objects.requireNonNull(shoppingListRepository, "shoppingListRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
        if (mealId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
