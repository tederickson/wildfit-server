package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealSummaryDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RetrieveMealHandlerTest extends CommonMealHandlerTest {

    @Test
    void execute() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Chili_Beef_Lettuce_Wraps__Summer_.json"));

        createRecipes(recipeDigestList);

        final var testUserId = "yabba-dabba-doo";
        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).collect(Collectors.toList());
        final var request = CreateMealRequest.builder()
                .withUuid(testUserId)
                .withRecipeIds(recipeIds).build();

        createMeal(request);

        final var response = mealService.retrieveMeal(mealDigest.getId(), testUserId);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStartDate());
        assertNotNull(response.getEndDate());
        assertEquals(recipeDigestList.size(), response.getRecipes().size());
    }

    @Test
    void execute_RecipeIsDeleted() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Chili_Beef_Lettuce_Wraps__Summer_.json"));

        createRecipes(recipeDigestList);

        final var testUserId = "yabba-dabba-doo";
        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).collect(Collectors.toList());
        final var request = CreateMealRequest.builder()
                .withUuid(testUserId)
                .withRecipeIds(recipeIds).build();

        createMeal(request);

        recipeRepository.deleteById(recipeDigests.get(1).getId());

        final var response = mealService.retrieveMeal(mealDigest.getId(), testUserId);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStartDate());
        assertNotNull(response.getEndDate());
        assertEquals(recipeDigestList.size(), response.getRecipes().size());

        // The deleted recipe shows up as a MealSummaryDigest with null id and recipeId
        final var deletedRecipes =
                response.getRecipes().stream().filter(x -> x.getRecipeId() == null).toList();
        assertEquals(1, deletedRecipes.size());
        assertEquals(MealSummaryDigest.builder().build(), deletedRecipes.getFirst());
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> RetrieveMealHandler.builder().build().execute());
    }

    @Test
    void missingUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveMeal(-1L, null));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveMeal(-1L, "  "));

        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingMealId() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveMeal(null, userId));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void mealNotFound() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.retrieveMeal(-1L, userId));
        assertEquals(WildfitServiceError.MEAL_NOT_FOUND, exception.getError());
    }
}