package com.wildfit.server.service;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;

public interface NutritionixService {
    FoodItemDigest getFoodWithBarcode(String barcode) throws UserServiceException, NutritionixException;
}
