package com.wildfit.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NutritionixServiceImplTest {
    @Autowired
    private NutritionixService nutritionixService;

    @Test
    void getFoodWithBarcode() throws UserServiceException {
        final var foodItemDigest = nutritionixService.getFoodWithBarcode("767707001258");
        System.out.println("foodItemDigest = " + foodItemDigest);
        assertNotNull(foodItemDigest);
        assertEquals("Kerrygold", foodItemDigest.getBrand_name());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullBarcode(String barcode) {
        final var exception = assertThrows(UserServiceException.class,
                () -> nutritionixService.getFoodWithBarcode(barcode));
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }
}