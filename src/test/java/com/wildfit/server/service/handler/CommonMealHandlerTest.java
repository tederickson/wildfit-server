package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.repository.MealRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonMealHandlerTest extends CommonRecipeHandlerTest {
    protected static MealDigest mealDigest;

    @Autowired
    protected MealRepository mealRepository;

    @AfterEach
    void tearDown() {
        if (mealDigest != null) {
            mealRepository.deleteById(mealDigest.getId());

            mealDigest = null;
        }

        super.tearDown();
    }

    protected void createMeal(final CreateMealRequest request) throws WildfitServiceException {
        mealDigest = CreateMealHandler.builder()
                                      .withMealRepository(mealRepository)
                                      .withRecipeRepository(recipeRepository)
                                      .withRequest(request)
                                      .build().execute();
    }
}
