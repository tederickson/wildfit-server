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
class ListBySeasonAndNameHandlerTest extends CommonRecipeHandlerTest {

    private static final String RECIPE_NAME = "TEST TEST TEST Chili Beef Lettuce Wraps";
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 100);


    @Test
    void execute() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest("Tuna_salad.json");
        recipe.setName(RECIPE_NAME);

        createRecipe(recipe);

        final var response = recipeService.listBySeasonAndName(SeasonType.SPRING, RECIPE_NAME, PAGE_REQUEST);

        final var foundRecipe = response.getRecipes().stream()
                .filter(x -> x.getId().equals(testRecipe.getId()))
                .findFirst();
        assertTrue(foundRecipe.isPresent());

        assertEquals(RECIPE_NAME, foundRecipe.get().getName());
    }

    @Test
    void execute_pageIndexDoesNotExist() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest("Tuna_salad.json");
        recipe.setName(RECIPE_NAME);

        createRecipe(recipe);

        final var response = recipeService.listBySeasonAndName(SeasonType.SPRING, RECIPE_NAME, PageRequest.of(10, 100));

        assertTrue(response.getRecipes().isEmpty());
    }

    @Test
    void missingSeason() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> recipeService.listBySeasonAndName(null, RECIPE_NAME, PAGE_REQUEST));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingRecipeName() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> recipeService.listBySeasonAndName(SeasonType.SPRING, null,
                                                                                   PAGE_REQUEST));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingPageable() {
        final var exception = assertThrows(WildfitServiceException.class,
                                           () -> recipeService.listBySeasonAndName(SeasonType.SPRING, RECIPE_NAME,
                                                                                   null));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }
}