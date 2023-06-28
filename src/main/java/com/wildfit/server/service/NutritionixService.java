package com.wildfit.server.service;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;

public interface NutritionixService {
    FoodItemDigest getFoodWithBarcode(String barcode) throws UserServiceException, NutritionixException;

    FoodItemDigest getFoodWithId(String nixItemId) throws UserServiceException, NutritionixException;

    SearchFoodResponse getFoodsByQuery(String description, String servingUnit) throws UserServiceException, NutritionixException;
}
