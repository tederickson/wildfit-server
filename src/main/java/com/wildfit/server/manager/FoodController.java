package com.wildfit.server.manager;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.NutritionixService;
import com.wildfit.server.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Api(description = "Food API")
@RequestMapping("v1/fooditems")
public class FoodController {
    @Autowired
    private NutritionixService nutritionixService;
    @Autowired
    private RecipeService recipeService;

    @ApiOperation(value = "Get food by id")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Get food", response = FoodItemDigest.class), //
            @ApiResponse(code = 400, message = "Nutritionix id not found")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public FoodItemDigest getFoodWithId(@PathVariable("id") String id)
            throws UserServiceException, NutritionixException {
        final var logMessage = String.join("|", "getFoodWithId", id);
        log.info(logMessage);

        return nutritionixService.getFoodWithId(id);
    }

    @ApiOperation(value = "Get food by barcode")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Get food", response = FoodItemDigest.class), //
            @ApiResponse(code = 400, message = "Barcode not found")})
    @GetMapping(value = "/barcodes/{barcode}", produces = "application/json")
    public FoodItemDigest getFoodWithBarcode(@PathVariable("barcode") String barcode)
            throws UserServiceException, NutritionixException {
        final var logMessage = String.join("|", "getFoodWithBarcode", barcode);
        log.info(logMessage);

        return nutritionixService.getFoodWithBarcode(barcode);
    }

    @ApiOperation(value = "Get foods by description such as 'coconut oil', 'kerrygold butter'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get foods", response = SearchFoodResponse.class)})
    @GetMapping(produces = "application/json")
    public SearchFoodResponse getFoodsByQuery(@RequestParam(name = "description") String description)
            throws UserServiceException, NutritionixException {
        final var logMessage = String.join("|", "getFoodsByQuery", description);
        log.info(logMessage);

        return nutritionixService.getFoodsByQuery(description);
    }

    @ApiOperation(value = "Get nutrition per serving for recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get recipe nutrition", response = FoodItemDigest.class)})
    @GetMapping(value = "/recipes/{recipeId}", produces = "application/json")
    public FoodItemDigest getRecipeNutrition(@RequestParam(name = "recipeId") Long recipeId)
            throws UserServiceException, NutritionixException {
        final var logMessage = String.join("|", "getRecipeNutrition", recipeId.toString());
        log.info(logMessage);

        RecipeDigest recipeDigest = recipeService.retrieveRecipe(recipeId);
        return nutritionixService.getRecipeNutrition(recipeDigest);
    }
}
