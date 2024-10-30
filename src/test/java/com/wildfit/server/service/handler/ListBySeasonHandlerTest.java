package com.wildfit.server.service.handler;

import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.WildfitServiceError;
import com.wildfit.server.service.RecipeService;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ListBySeasonHandlerTest extends CommonRecipeHandlerTest {
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 100);

    @Autowired
    private RecipeService recipeService;

    @Test
    void execute() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest("Tuna_salad.json");
        recipe.setName("TEST TEST TEST Chili Beef Lettuce Wraps");

        createRecipe(recipe);

        final var response = recipeService.listBySeason(SeasonType.SPRING, PAGE_REQUEST);

        final var foundRecipe = response.getRecipes().stream()
                                        .filter(x -> x.getId().equals(testRecipe.getId()))
                                        .findFirst();
        assertTrue(foundRecipe.isPresent());

        assertEquals("TEST TEST TEST Chili Beef Lettuce Wraps", foundRecipe.get().getName());
    }

    @Test
    void missingSeason() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> recipeService.listBySeason(null, PAGE_REQUEST));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingPageable() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                                           () -> recipeService.listBySeason(SeasonType.SPRING, null));
        assertEquals(WildfitServiceError.INVALID_PARAMETER, exception.getError());
    }

}