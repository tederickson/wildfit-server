package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.util.ReadRecipeDigest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class ListBySeasonHandlerTest extends CommonRecipeHandlerTest {

    @Test
    void execute() throws Exception {
        final var recipe = ReadRecipeDigest.getRecipeDigest("Tuna_salad.json");
        recipe.setName("TEST TEST TEST Chili Beef Lettuce Wraps");

        createRecipe(recipe);

        final var response = ListBySeasonHandler.builder()
                                                .withSeason(SeasonType.SPRING)
                                                .withPageable(PageRequest.of(0, 100))
                                                .withRecipeRepository(recipeRepository)
                                                .build()
                                                .execute();

        final var foundRecipe = response.getRecipes().stream()
                                        .filter(x -> x.getId().equals(testRecipe.getId()))
                                        .findFirst();
        assertTrue(foundRecipe.isPresent());

        assertEquals("TEST TEST TEST Chili Beef Lettuce Wraps", foundRecipe.get().getName());
    }

    @Test
    void missingSeason() {
        final var exception = assertThrows(UserServiceException.class,
                () -> ListBySeasonHandler.builder()
                                         .withPageable(PageRequest.of(1, 100))
                                         .withRecipeRepository(recipeRepository)
                                         .build()
                                         .execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

    @Test
    void missingPageable() {
        final var exception = assertThrows(UserServiceException.class,
                () -> ListBySeasonHandler.builder()
                                         .withSeason(SeasonType.SPRING)
                                         .withRecipeRepository(recipeRepository)
                                         .build()
                                         .execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

}