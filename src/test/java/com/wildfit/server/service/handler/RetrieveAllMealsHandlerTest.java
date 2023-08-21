package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.wildfit.server.domain.CreateMealRequest;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RetrieveAllMealsHandlerTest extends CommonMealHandlerTest {

    @Test
    void execute() throws WildfitServiceException {
        List<RecipeDigest> recipeDigestList = new ArrayList<>();

        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad.json"));
        recipeDigestList.add(ReadRecipeDigest.getRecipeDigest("Tuna_salad_with_apple_and_celery.json"));

        createRecipes(recipeDigestList);

        final var testUserId = "yabba-dabba-doo";
        final var recipeIds = recipeDigests.stream().map(RecipeDigest::getId).collect(Collectors.toList());
        final var request = CreateMealRequest.builder()
                                             .withUuid(testUserId)
                                             .withRecipeIds(recipeIds).build();

        createMeal(request);

        final var responseList = RetrieveAllMealsHandler.builder()
                                                        .withMealRepository(mealRepository)
                                                        .withRecipeRepository(recipeRepository)
                                                        .withUserId(testUserId)
                                                        .build().execute();
        assertNotNull(responseList);
        assertEquals(1, responseList.size());

        final var response = responseList.get(0);
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
                () -> RetrieveAllMealsHandler.builder()
                                             .withMealRepository(mealRepository)
                                             .withRecipeRepository(recipeRepository)
                                             .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void blankUserId() {
        final var exception = assertThrows(WildfitServiceException.class,
                () -> RetrieveAllMealsHandler.builder()
                                             .withMealRepository(mealRepository)
                                             .withRecipeRepository(recipeRepository)
                                             .withUserId("  ")
                                             .build().execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }
}