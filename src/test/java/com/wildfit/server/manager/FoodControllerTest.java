package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wildfit.server.domain.FoodItemDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SearchFoodResponse;
import com.wildfit.server.exception.NutritionixException;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.NutritionixService;
import com.wildfit.server.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FoodControllerTest {
    @InjectMocks
    FoodController foodController;
    @Mock
    private NutritionixService nutritionixService;
    @Mock
    private RecipeService recipeService;

    @Test
    void getFoodWithId() throws WildfitServiceException, NutritionixException {
        final var id = "123";
        when(nutritionixService.getFoodWithId(id)).thenReturn(FoodItemDigest.builder().build());

        final var response = foodController.getFoodWithId(id);
        assertNotNull(response);
    }

    @Test
    void getFoodWithBarcode() throws WildfitServiceException, NutritionixException {
        final var barcode = "12345";
        when(nutritionixService.getFoodWithBarcode(barcode)).thenReturn(FoodItemDigest.builder().build());

        final var response = foodController.getFoodWithBarcode(barcode);
        assertNotNull(response);
    }

    @Test
    void getFoodsByQuery() throws WildfitServiceException, NutritionixException {
        final var description = "blah de blah, blah blah";
        when(nutritionixService.getFoodsByQuery(any())).thenReturn(new SearchFoodResponse());

        final var response = foodController.getFoodsByQuery(description);
        assertNotNull(response);
    }

    @Test
    void getRecipeNutrition() throws WildfitServiceException, NutritionixException {
        when(recipeService.retrieveRecipe(any())).thenReturn(RecipeDigest.builder().build());
        when(nutritionixService.getRecipeNutrition(any())).thenReturn(FoodItemDigest.builder().build());

        final var response = foodController.getRecipeNutrition(12L);
        assertNotNull(response);
    }
}