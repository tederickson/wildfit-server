package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.MealDigest;
import com.wildfit.server.domain.MealSummaryDigest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UpdateMealHandlerTest extends CommonMealHandlerTest {
    @Test
    void execute() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json"));

        createRecipes(recipeDigestList);

        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).toList();
        final var request = CreateMealRequest.builder()
                .withUuid(userId)
                .withRecipeIds(recipeIds).build();

        createMeal(request);

        assertNotNull(mealDigest.getId());
        assertEquals(userId, mealDigest.getUuid());

        final var expectedDate = LocalDate.now();
        assertEquals(expectedDate, mealDigest.getStartDate());
        assertEquals(expectedDate, mealDigest.getEndDate());
        assertEquals(recipeIds.size(), mealDigest.getRecipes().size());

        // Update plan dates and cooked
        var planDate = LocalDate.now().minusDays(1);
        for (var mealSummary : mealDigest.getRecipes()) {
            assertNull(mealSummary.getPlanDate());
            assertFalse(mealSummary.isCooked());

            mealSummary.setPlanDate(planDate);
            mealSummary.setCooked(true);

            planDate = planDate.plusDays(1);
        }

        final var response = mealService.updateMeal(mealDigest);

        assertEquals(expectedDate.minusDays(1), response.getStartDate());
        assertEquals(planDate.minusDays(1), response.getEndDate());
        assertEquals(recipeIds.size(), mealDigest.getRecipes().size());

        for (var mealSummary : mealDigest.getRecipes()) {
            assertNotNull(mealSummary.getPlanDate());
            assertTrue(mealSummary.isCooked());
        }
    }

    @Test
    void addAnotherRecipe() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json"));

        createRecipes(recipeDigestList);

        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).toList();
        final var request = CreateMealRequest.builder()
                .withUuid(userId)
                .withRecipeIds(List.of(recipeIds.getFirst())).build();

        createMeal(request);

        assertNotNull(mealDigest.getId());
        assertEquals(userId, mealDigest.getUuid());

        final var expectedDate = LocalDate.now();
        assertEquals(expectedDate, mealDigest.getStartDate());
        assertEquals(expectedDate, mealDigest.getEndDate());
        assertEquals(1, mealDigest.getRecipes().size());

        // Add the two recipes
        mealDigest.getRecipes().add(MealSummaryDigest.builder().withRecipeId(recipeIds.get(1)).build());
        mealDigest.getRecipes().add(MealSummaryDigest.builder().withRecipeId(recipeIds.get(2)).build());

        final var response = mealService.updateMeal(mealDigest);

        assertEquals(recipeIds.size(), response.getRecipes().size());

        for (var mealSummary : response.getRecipes()) {
            assertTrue(recipeIds.contains(mealSummary.getRecipeId()));
        }
    }

    @Test
    void updateNonExistentRecipe() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Egg_muffins_with_mushrooms_and_herbs.json"));

        createRecipes(recipeDigestList);

        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).toList();
        final var request = CreateMealRequest.builder()
                .withUuid(userId)
                .withRecipeIds(recipeIds).build();

        createMeal(request);

        mealDigest.getRecipes().forEach(x -> x.setCooked(true));
        mealDigest.getRecipes().getFirst().setRecipeId(-100L);

        final var response = mealService.updateMeal(mealDigest);
        assertEquals(1, response.getRecipes().size());
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> UpdateMealHandler.builder().build().execute());
    }

    @Test
    void missingRequest() {
        assertThrows(NullPointerException.class,
                     () -> UpdateMealHandler.builder()
                             .withMealRepository(mealRepository)
                             .withRecipeRepository(recipeRepository)
                             .build().execute());
    }

    @Test
    void missingUserId() {
        final var request = MealDigest.builder().build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.updateMeal(request));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var request = MealDigest.builder().withUuid("  ").build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.updateMeal(request));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingMealSummaryDigest() {
        final var request = MealDigest.builder().withUuid(userId).build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.updateMeal(request));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void invalidMealId() {
        final var request = MealDigest.builder()
                .withUuid(userId)
                .withId(-9L)
                .withRecipes(List.of(MealSummaryDigest.builder()
                                             .withId(95L)
                                             .withRecipeId(14L)
                                             .build()))
                .build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> mealService.updateMeal(request));
        assertEquals(WildfitServiceError.MEAL_NOT_FOUND, exception.getError());
    }
}