package com.wildfit.server.manager;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {
    static final Long mealId = 123456L;
    static final String userId = "abra-cadabra";

    private MealController mealController;

    @Mock
    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealController = new MealController(mealService);
    }

    @Test
    void retrieveAllMeals() throws WildfitServiceException {
        final var response = mealController.retrieveAllMeals(userId, 14, 30);
        assertTrue(response.isEmpty());
    }

    @Test
    void retrieveMeal() throws WildfitServiceException {
        final var response = mealController.retrieveMeal(mealId, userId);
        assertNull(response);
    }

    @Test
    void deleteMeal() throws WildfitServiceException {
        mealController.deleteMeal(mealId, userId);
    }

    @Test
    void createMeal() throws WildfitServiceException {
        final var response = mealController.createMeal(CreateMealRequest.builder().build());
        assertNull(response);
    }

    @Test
    void updateMeal() throws WildfitServiceException {
        final var response = mealController.updateMeal(null);
        assertNull(response);
    }
}