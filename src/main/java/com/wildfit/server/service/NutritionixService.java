package com.wildfit.server.service;
import com.wildfit.server.exception.*;
import com.wildfit.server.domain.FoodItemDigest;

public interface NutritionixService {
    FoodItemDigest getFoodWithBarcode(String barcode)throws UserServiceException;
}
