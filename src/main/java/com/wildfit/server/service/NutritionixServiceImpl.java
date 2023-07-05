package com.wildfit.server.service;

import java.util.List;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.InstructionGroupDigest;
import com.wildfit.server.domain.ParseRecipeRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.NutritionixHeaderInfo;
import com.wildfit.server.service.handler.GetFoodWithBarcodeHandler;
import com.wildfit.server.service.handler.GetFoodWithIdHandler;
import com.wildfit.server.service.handler.GetFoodsByQueryHandler;
import com.wildfit.server.service.handler.GetRecipeNutritionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service calls Handlers to implement the functionality.
 * This provides loose coupling, allows several people to work on the service without merge collisions
 * and provides a lightweight service.
 */
@Slf4j
@Service
public class NutritionixServiceImpl implements NutritionixService {

    @Autowired
    private NutritionixHeaderInfo nutritionixHeaderInfo;

    @Override
    public FoodItemDigest getFoodWithBarcode(String barcode) throws UserServiceException, NutritionixException {
        return GetFoodWithBarcodeHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withBarcode(barcode)
                .build().execute();
    }

    @Override
    public FoodItemDigest getFoodWithId(String nixItemId) throws UserServiceException, NutritionixException {
        return GetFoodWithIdHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withNixItemId(nixItemId)
                .build().execute();
    }

    @Override
    public SearchFoodResponse getFoodsByQuery(String description)
            throws UserServiceException, NutritionixException {
        return GetFoodsByQueryHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withDescription(description)
                .build().execute();
    }

    @Override
    public FoodItemDigest getRecipeNutrition(RecipeDigest recipeDigest) throws UserServiceException, NutritionixException {
        final var parseRecipeRequest = new ParseRecipeRequest();
        parseRecipeRequest.setAggregate(recipeDigest.getName());
        parseRecipeRequest.setNum_servings(recipeDigest.getServingQty());

        recipeDigest.getInstructionGroups().stream()
                .map(InstructionGroupDigest::getIngredients)
                .flatMap(List::stream)
                .forEach((ingredient) -> parseRecipeRequest.addIngredient(
                        ingredient.getServingQty(),
                        ingredient.getServingUnit(),
                        ingredient.getFoodName()));

        return GetRecipeNutritionHandler.builder()
                .withNutritionixHeaderInfo(nutritionixHeaderInfo)
                .withParseRecipeRequest(parseRecipeRequest)
                .build().execute();
    }

}
