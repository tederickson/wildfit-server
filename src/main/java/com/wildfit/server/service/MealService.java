package com.wildfit.server.service;

import java.util.List;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceException;
import org.springframework.data.domain.Pageable;

public interface MealService {
    List<MealDigest> retrieveAllMeals(String userId, Pageable pageable) throws WildfitServiceException;

    MealDigest retrieveMeal(Long mealId, String userId) throws WildfitServiceException;

    void deleteMeal(Long mealId, String userId) throws WildfitServiceException;

    MealDigest createMeal(CreateMealRequest request) throws WildfitServiceException;

    MealDigest updateMeal(MealDigest request) throws WildfitServiceException;
}
