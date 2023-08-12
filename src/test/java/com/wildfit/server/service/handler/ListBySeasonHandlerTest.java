package com.wildfit.server.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class ListBySeasonHandlerTest extends CommonRecipeHandlerTest {

    @Test
    void execute() throws UserServiceException {
        final var recipe = RecipeDigest.builder()
                                       .withName("TEST TEST TEST Chili Beef Lettuce Wraps")
                                       .withSeason(SeasonType.SPRING)
                                       .withIntroduction("TEST TEST TEST")
                                       .withPrepTimeMin(5)
                                       .withCookTimeMin(15)
                                       .withServingQty(4)
                                       .withServingUnit("serving")
                                       .build();
        createRecipe(recipe);

        final var response = ListBySeasonHandler.builder()
                                                .withSeason(SeasonType.SPRING)
                                                .withPageable(PageRequest.of(0, 100))
                                                .withRecipe1Repository(recipe1Repository)
                                                .build()
                                                .execute();

        final var foundRecipe = response.getRecipes().stream().filter(x -> x.getId() == testRecipe.getId()).findFirst();
        assertTrue(foundRecipe.isPresent());
    }

    @Test
    void missingSeason() {
        final var exception = assertThrows(UserServiceException.class,
                () -> ListBySeasonHandler.builder()
                                         .withPageable(PageRequest.of(1, 100))
                                         .withRecipe1Repository(recipe1Repository)
                                         .build()
                                         .execute());
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }
}