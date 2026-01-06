package com.wildfit.server.service.handler;

import com.wildfit.server.domain.CreateMealRequest;
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

@SpringBootTest
class CreateMealHandlerIT extends CommonMealHandlerTest {

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

        for (var mealSummary : mealDigest.getRecipes()) {
            assertNull(mealSummary.getPlanDate());
            assertFalse(mealSummary.isCooked());
            assertNotNull(mealSummary.getThumbnail());

            final var recipe =
                    recipeDigests.stream().filter(x -> mealSummary.getRecipeId().equals(x.getId()))
                            .findFirst().orElseThrow();
            assertEquals(recipe.getName(), mealSummary.getName());
            assertEquals(recipe.getSeason(), mealSummary.getSeason());
        }
    }

    @Test
    void nullParameters() {
        assertThrows(NullPointerException.class,
                     () -> CreateMealHandler.builder().build().execute());
    }

    @Test
    void missingRequest() {
        assertThrows(NullPointerException.class,
                     () -> CreateMealHandler.builder()
                             .withMealRepository(mealRepository)
                             .withRecipeRepository(recipeRepository)
                             .build().execute());
    }

    @Test
    void missingUserId() {
        final var request = CreateMealRequest.builder().withRecipeIds(List.of(3L)).build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> CreateMealHandler.builder()
                                                   .withMealRepository(mealRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRequest(request)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var request = CreateMealRequest.builder()
                .withUuid("  ")
                .withRecipeIds(List.of(3L)).build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> CreateMealHandler.builder()
                                                   .withMealRepository(mealRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRequest(request)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingRecipeIds() {
        final var request = CreateMealRequest.builder()
                .withUuid(userId)
                .build();
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> CreateMealHandler.builder()
                                                   .withMealRepository(mealRepository)
                                                   .withRecipeRepository(recipeRepository)
                                                   .withRequest(request)
                                                   .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }
}