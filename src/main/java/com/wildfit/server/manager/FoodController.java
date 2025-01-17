package com.wildfit.server.manager;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.NutritionixService;
import com.wildfit.server.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Food API")
@RequestMapping("v1/foods")
public class FoodController {
    private final NutritionixService nutritionixService;
    private final RecipeService recipeService;

    @Operation(summary = "Get food by id")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get food"), //
            @ApiResponse(responseCode = "400", description = "Nutritionix id not found")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public FoodItemDigest getFoodWithId(@PathVariable String id)
            throws WildfitServiceException, NutritionixException {
        return nutritionixService.getFoodWithId(id);
    }

    @Operation(summary = "Get food by barcode")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get food"), //
            @ApiResponse(responseCode = "400", description = "Barcode not found")})
    @GetMapping(value = "/barcodes/{barcode}", produces = "application/json")
    public FoodItemDigest getFoodWithBarcode(@PathVariable String barcode)
            throws WildfitServiceException, NutritionixException {
        return nutritionixService.getFoodWithBarcode(barcode);
    }

    @Operation(summary = "Get foods by description such as 'coconut oil', 'kerrygold butter'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get foods")})
    @GetMapping(produces = "application/json")
    public SearchFoodResponse getFoodsByQuery(@RequestParam String description)
            throws WildfitServiceException, NutritionixException {
        return nutritionixService.getFoodsByQuery(description);
    }

    @Operation(summary = "Get nutrition per serving for recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get recipe nutrition")})
    @GetMapping(value = "/recipes/{recipeId}", produces = "application/json")
    public FoodItemDigest getRecipeNutrition(@RequestParam Long recipeId)
            throws WildfitServiceException, NutritionixException {
        RecipeDigest recipeDigest = recipeService.retrieveRecipe(recipeId);
        return nutritionixService.getRecipeNutrition(recipeDigest);
    }
}
