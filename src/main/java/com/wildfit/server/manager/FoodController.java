package com.wildfit.server.manager;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.UserProfileDigest;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Exceptions are handled by the ManagerAdvice. The exception is logged
 * and converted to the appropriate HTTP Status code.
 */
@RestController
@Slf4j
@Api(description = "Food API")
public class FoodController {
    @Autowired
    private NutritionixService nutritionixService;

    @ApiOperation(value = "Get food matching barcode")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Get food", response = UserProfileDigest.class), //
            @ApiResponse(code = 400, message = "Barcode not found")})
    @GetMapping("/fooditems/{barcode}")
    public FoodItemDigest getFoodWithBarcode(@PathVariable("barcode") String barcode) throws UserServiceException {
        final var logMessage = String.join("|", "getFoodWithBarcode", barcode);
        log.info(logMessage);

        return nutritionixService.getFoodWithBarcode(barcode);
    }
}
