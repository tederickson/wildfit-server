package com.wildfit.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.MealRepository;
import com.wildfit.server.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MealServiceImplTest {
    @Mock
    MealRepository mealRepository;
    @Mock
    RecipeRepository recipeRepository;

    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealServiceImpl(recipeRepository, mealRepository);
    }

    @Test
    void retrieveMeal() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> mealService.retrieveMeal(null, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void retrieveAllMeals() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> mealService.retrieveAllMeals(null, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void deleteMeal() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> mealService.deleteMeal(null, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void createMeal() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> mealService.createMeal(CreateMealRequest.builder().build()));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void updateMeal() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> mealService.updateMeal(MealDigest.builder().build()));
        assertEquals("Invalid parameter.", exception.getMessage());
    }
}