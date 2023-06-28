package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.NutritionixService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodControllerTest {
    @Mock
    private NutritionixService nutritionixService;

    @InjectMocks
    FoodController foodController;

    @Test
    void getFoodWithId() throws UserServiceException, NutritionixException {
        final var id = "123";
        when(nutritionixService.getFoodWithId(id)).thenReturn(FoodItemDigest.builder().build());

        final var response = foodController.getFoodWithId(id);
        assertNotNull(response);
    }

    @Test
    void getFoodWithBarcode() throws UserServiceException, NutritionixException {
        final var barcode = "12345";
        when(nutritionixService.getFoodWithBarcode(barcode)).thenReturn(FoodItemDigest.builder().build());

        final var response = foodController.getFoodWithBarcode(barcode);
        assertNotNull(response);
    }

    @Test
    void getFoodsByQuery() throws UserServiceException, NutritionixException {
        final var description = "blah de blah, blah blah";
        String servingUnit = null;

        when(nutritionixService.getFoodsByQuery(any(), any())).thenReturn(new SearchFoodResponse());

        final var response = nutritionixService.getFoodsByQuery(description, servingUnit);
        assertNotNull(response);
    }
}