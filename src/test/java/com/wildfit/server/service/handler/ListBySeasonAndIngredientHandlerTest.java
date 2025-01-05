package com.wildfit.server.service.handler;

import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.exception.WildfitServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ListBySeasonAndIngredientHandlerTest extends CommonRecipeHandlerTest {

    private static final String RECIPE_NAME = "ListBySeasonAndIngredientHandlerTest";
    private static final String ingredientName = "mushrooms";
    private static final String FILE_NAME = "Egg_muffins_with_mushrooms_and_herbs.json";
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 100);


    @Test
    void execute() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest(FILE_NAME);
        recipe.setName(RECIPE_NAME);

        createRecipe(recipe);

        final var response = recipeService.listBySeasonAndIngredient(SeasonType.SPRING, ingredientName, PAGE_REQUEST);

        final var foundRecipe = response.recipes().stream()
                .filter(x -> x.id().equals(testRecipe.getId()))
                .findFirst();
        assertTrue(foundRecipe.isPresent());

        assertEquals(RECIPE_NAME, foundRecipe.get().name());
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

        assertTrue(response.recipes().isEmpty());
    }

    @Test
    void missingSeason() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> ListBySeasonAndIngredientHandler.builder()
                                                   .withPageable(PAGE_REQUEST)
                                                   .withIngredientName(ingredientName)
                                                   .withRecipeRepository(recipeRepository)
                                                   .build()
                                                   .execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingIngredientName() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> ListBySeasonAndIngredientHandler.builder()
                                                   .withSeason(SeasonType.SPRING)
                                                   .withPageable(PAGE_REQUEST)
                                                   .withRecipeRepository(recipeRepository)
                                                   .build()
                                                   .execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingPageable() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> ListBySeasonAndIngredientHandler.builder()
                                                   .withSeason(SeasonType.SPRING)
                                                   .withIngredientName(ingredientName)
                                                   .withRecipeRepository(recipeRepository)
                                                   .build()
                                                   .execute());
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }
}