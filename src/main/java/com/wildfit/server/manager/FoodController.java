package com.wildfit.server.manager;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.NutritionixService;
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

    @ApiOperation(value = "Get food by id")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Get food", response = FoodItemDigest.class), //
            @ApiResponse(code = 400, message = "Barcode not found")})
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

    @ApiOperation(value = "Get foods by description. You can provide a description of '2 tsp coconut oil' " +
            "or you can narrow the search by setting serving_unit='tsp'.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get foods", response = SearchFoodResponse.class)})
    @GetMapping(produces = "application/json")
    public SearchFoodResponse getFoodsByQuery(@RequestParam(name = "description") String description,
                                              @RequestParam(name = "serving_unit", required = false) String servingUnit,
                                              @RequestParam(name = "serving_quantity", required = false) String servingQuantity)
            throws UserServiceException, NutritionixException {
        final var logMessage = String.join("|", "getFoodsByQuery",
                description, servingUnit, servingQuantity);
        log.info(logMessage);

        return nutritionixService.getFoodsByQuery(description, servingUnit);
    }
}
