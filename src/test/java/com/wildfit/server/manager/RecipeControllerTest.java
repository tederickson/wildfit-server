package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipeControllerTest {
    static final Long userId = -23L;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    @Test
    void retrieveRecipesForSeason() throws UserServiceException {
        when(recipeService.listBySeason(any(), any())).thenReturn(new RecipeListDigest());
        final var response = recipeController.retrieveRecipesForSeason(SeasonType.WINTER, 12, 40);

        assertNotNull(response);
    }

    @Test
    void retrieveRecipe() throws UserServiceException {
        when(recipeService.retrieveRecipe(any())).thenReturn(RecipeDigest.builder().build());

        final var response = recipeController.retrieveRecipe(15L);

        assertNotNull(response);
    }

    @Test
    void deleteRecipe() throws UserServiceException {
        recipeController.deleteRecipe(-15L, userId);
    }

    @Test
    void createRecipe() throws UserServiceException {
        when(recipeService.createRecipe(any(), any())).thenReturn(RecipeDigest.builder().build());

        final var response = recipeController.createRecipe(userId, RecipeDigest.builder().build());
        assertNotNull(response);
    }

    @Test
    void updateRecipe() throws UserServiceException {
        when(recipeService.updateRecipe(any(), any())).thenReturn(RecipeDigest.builder().build());

        final var response = recipeController.updateRecipe(userId, RecipeDigest.builder().build());
        assertNotNull(response);
    }
}