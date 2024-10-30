package com.wildfit.server.service;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.model.NutritionixHeaderInfo;
import com.wildfit.server.service.handler.GetFoodWithBarcodeHandler;
import com.wildfit.server.service.handler.GetFoodWithIdHandler;
import com.wildfit.server.service.handler.GetFoodsByQueryHandler;
import com.wildfit.server.service.handler.GetRecipeNutritionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, a lightweight service, separation of concerns, and allows several people to work on
 * the same service without merge collisions.
 */
@Service
@RequiredArgsConstructor
public class NutritionixServiceImpl implements NutritionixService {
    private final NutritionixHeaderInfo nutritionixHeaderInfo;

    @Override
    public FoodItemDigest getFoodWithBarcode(String barcode) throws WildfitServiceException, NutritionixException {
        return GetFoodWithBarcodeHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withBarcode(barcode)
                .build().execute();
    }

    @Override
    public FoodItemDigest getFoodWithId(String nixItemId) throws WildfitServiceException, NutritionixException {
        return GetFoodWithIdHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withNixItemId(nixItemId)
                .build().execute();
    }

    @Override
    public SearchFoodResponse getFoodsByQuery(String description) throws WildfitServiceException, NutritionixException {
        return GetFoodsByQueryHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withDescription(description)
                .build().execute();
    }

    @Override
    public FoodItemDigest getRecipeNutrition(RecipeDigest recipeDigest)
            throws WildfitServiceException, NutritionixException {
        return GetRecipeNutritionHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withRecipeDigest(recipeDigest)
                .build().execute();
    }

}
