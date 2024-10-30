package com.wildfit.server.service;

import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class NutritionixServiceImplTest {
    @Value("${bypass.Nutritionix.Service:true}")
    private boolean bypassNutritionixService;

    @Autowired
    private NutritionixService nutritionixService;

    @Test
    void getFoodWithBarcode() throws WildfitServiceException, NutritionixException {
        if (bypassNutritionixService) {
            return;
        }
        final var foodItemDigest = nutritionixService.getFoodWithBarcode("767707001258");

        nutritionixService.getFoodWithBarcode("767707001258");
        assertNotNull(foodItemDigest);
        assertEquals("Kerrygold", foodItemDigest.getBrandName());
    }

    @Test
    void barcodeNotFound() {
        if (bypassNutritionixService) {
            return;
        }
        final var exception = assertThrows(NutritionixException.class,
                                           () -> nutritionixService.getFoodWithBarcode("327594"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullBarcode(String barcode) {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> nutritionixService.getFoodWithBarcode(barcode));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void getFoodWithId_badParameters() {
        assertThrows(WildfitServiceException.class,
                     () -> nutritionixService.getFoodWithId(null));
    }

    @Test
    void getFoodsByQuery_badParameters() {
        assertThrows(WildfitServiceException.class,
                     () -> nutritionixService.getFoodsByQuery(null));
    }

    @Test
    void getRecipeNutrition_badParameters() {
        assertThrows(WildfitServiceException.class,
                     () -> nutritionixService.getRecipeNutrition(null));
    }
}