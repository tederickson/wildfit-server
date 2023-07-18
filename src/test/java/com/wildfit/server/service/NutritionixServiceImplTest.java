package com.wildfit.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class NutritionixServiceImplTest {
    @Value("${bypass.Nutritionix.Service:true}")
    private boolean bypassNutritionixService;

    @Autowired
    private NutritionixService nutritionixService;

    @Test
    void getFoodWithBarcode() throws UserServiceException, NutritionixException {
        if (bypassNutritionixService) {
            return;
        }
        final var foodItemDigest = nutritionixService.getFoodWithBarcode("767707001258");
        System.out.println("foodItemDigest = " + foodItemDigest);
        assertNotNull(foodItemDigest);
        assertEquals("Kerrygold", foodItemDigest.getBrandName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullBarcode(String barcode) {
        if (bypassNutritionixService) {
            return;
        }
        final var exception = assertThrows(UserServiceException.class,
                () -> nutritionixService.getFoodWithBarcode(barcode));
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
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
}