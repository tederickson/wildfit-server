package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.Recipe;
import com.wildfit.server.model.User;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import com.wildfit.server.repository.ShoppingListRepository;
import com.wildfit.server.repository.UserRepository;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class GetShoppingListHandler {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;
    private final ShoppingListRepository shoppingListRepository;

    private final String userId;

    private Recipe getRecipe(Long recipeId) throws WildfitServiceException {
        return recipeRepository.findById(recipeId)
                               .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.RECIPE_NOT_FOUND));

    }

    private User getUser() throws WildfitServiceException {
        return userRepository.findByUuid(userId)
                             .orElseThrow(() -> new WildfitServiceException(WildfitServiceError.USER_NOT_FOUND));

    }

    protected void validate() throws WildfitServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(mealRepository, "mealRepository");
        Objects.requireNonNull(recipeRepository, "recipeRepository");
        Objects.requireNonNull(shoppingListRepository, "shoppingListRepository");

        if (userId == null) {
            throw new WildfitServiceException(WildfitServiceError.INVALID_PARAMETER);
        }
    }
}
