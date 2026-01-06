package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RetrieveAllMealsHandlerIT extends CommonMealHandlerTest {

    public static final PageRequest PAGE_REQUEST = PageRequest.of(0, 100);

    @Test
    void execute() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));

        createRecipes(recipeDigestList);

        final var testUserId = "yabba-dabba-doo";
        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).toList();
        final var request = CreateMealRequest.builder()
                .withUuid(testUserId)
                .withRecipeIds(recipeIds).build();

        createMeal(request);

        final var responseList = mealService.retrieveAllMeals(testUserId, PAGE_REQUEST);
        assertNotNull(responseList);
        assertEquals(1, responseList.size());

        final var response = responseList.getFirst();
        assertNotNull(response.getId());
        assertNotNull(response.getStartDate());
        assertNotNull(response.getEndDate());
        assertEquals(recipeDigestList.size(), response.getRecipes().size());
    }


    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> RetrieveAllMealsHandler.builder().build().execute());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveAllMeals(null, PAGE_REQUEST));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveAllMeals("  ", PAGE_REQUEST));

        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingPageRequest() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveAllMeals(userId, null));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void mealNotFound() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveAllMeals("invalid-user", PAGE_REQUEST));
        assertEquals(WildfitServiceError.MEAL_NOT_FOUND, exception.getError());
    }
}