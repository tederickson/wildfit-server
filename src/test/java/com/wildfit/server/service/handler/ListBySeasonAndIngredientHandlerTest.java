package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class ListBySeasonAndIngredientHandlerTest extends CommonRecipeHandlerTest {

    private static final String RECIPE_NAME = "ListBySeasonAndIngredientHandlerTest";
    private static final String ingredientName = "mushrooms";
    private static final String FILE_NAME = "Egg_muffins_with_mushrooms_and_herbs.json";

    @Test
    void execute() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest(FILE_NAME);
        recipe.setName(RECIPE_NAME);

        createRecipe(recipe);

        final var response = ListBySeasonAndIngredientHandler.builder()
                                                             .withSeason(SeasonType.SPRING)
                                                             .withPageable(PageRequest.of(0, 100))
                                                             .withIngredientName(ingredientName)
                                                             .withRecipeRepository(recipeRepository)
                                                             .build()
                                                             .execute();

        final var foundRecipe = response.getRecipes().stream()
                                        .filter(x -> x.getId().equals(testRecipe.getId()))
                                        .findFirst();
        assertTrue(foundRecipe.isPresent());

        assertEquals(RECIPE_NAME, foundRecipe.get().getName());
    }

    @Test
    void execute_pageIndexDoesNotExist() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest(FILE_NAME);
        recipe.setName(RECIPE_NAME);

        createRecipe(recipe);

        final var response = ListBySeasonAndIngredientHandler.builder()
                                                             .withSeason(SeasonType.SPRING)
                                                             .withPageable(PageRequest.of(10, 100))
                                                             .withIngredientName(ingredientName)
                                                             .withRecipeRepository(recipeRepository)
                                                             .build()
                                                             .execute();

        assertTrue(response.getRecipes().isEmpty());
    }

    @Test
    void missingSeason() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> ListBySeasonAndIngredientHandler.builder()
                                                      .withPageable(PageRequest.of(1, 100))
                                                      .withIngredientName(ingredientName)
                                                      .withRecipeRepository(recipeRepository)
                                                      .build()
                                                      .execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingIngredientName() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> ListBySeasonAndIngredientHandler.builder()
                                                      .withSeason(SeasonType.SPRING)
                                                      .withPageable(PageRequest.of(1, 100))
                                                      .withRecipeRepository(recipeRepository)
                                                      .build()
                                                      .execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingPageable() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> ListBySeasonAndIngredientHandler.builder()
                                                      .withSeason(SeasonType.SPRING)
                                                      .withIngredientName(ingredientName)
                                                      .withRecipeRepository(recipeRepository)
                                                      .build()
                                                      .execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }
}