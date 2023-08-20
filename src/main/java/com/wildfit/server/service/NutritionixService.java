package com.wildfit.server.service;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.WildfitServiceException;

public interface NutritionixService {
    FoodItemDigest getFoodWithBarcode(String barcode) throws WildfitServiceException, NutritionixException;

    FoodItemDigest getFoodWithId(String nixItemId) throws WildfitServiceException, NutritionixException;

    SearchFoodResponse getFoodsByQuery(String description) throws WildfitServiceException, NutritionixException;

    FoodItemDigest getRecipeNutrition(RecipeDigest recipeDigest) throws WildfitServiceException, NutritionixException;
}
