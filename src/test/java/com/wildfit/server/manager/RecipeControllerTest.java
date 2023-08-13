package com.wildfit.server.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.domain.RecipeListDigest;
import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
    static final Long recipeId = 123L;
    static final String userId = "abra-cadabra";
    @InjectMocks
    RecipeController recipeController;
    @Mock
    private RecipeService recipeService;

    @Test
    void retrieveRecipesForSeason() throws UserServiceException {
        when(recipeService.listBySeason(any(), any())).thenReturn(new RecipeListDigest());

        final var response = recipeController.retrieveRecipesForSeason(SeasonType.FALL, 12, 40);
        assertNotNull(response);
    }

    @Test
    void listBySeasonAndIngredient() throws UserServiceException {
        when(recipeService.listBySeasonAndIngredient(any(), any(), any())).thenReturn(new RecipeListDigest());

        final var response = recipeController.listBySeasonAndIngredient(SeasonType.FALL,
                "iceberg lettuce", 3, 40);
        assertNotNull(response);
    }

    @Test
    void listBySeasonAndName() throws UserServiceException {
        when(recipeService.listBySeasonAndName(any(), any(), any())).thenReturn(new RecipeListDigest());

        final var response = recipeController.listBySeasonAndName(SeasonType.FALL,
                "lettuce wraps", 3, 40);
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

        final var response = recipeController.updateRecipe(recipeId, userId,
                RecipeDigest.builder().withId(recipeId).build());
        assertNotNull(response);
    }

    @Test
    void updateRecipe_idsDoNotMatch() {
        final var exception = assertThrows(UserServiceException.class,
                () -> recipeController.updateRecipe(recipeId, userId, RecipeDigest.builder().build()));
        assertEquals(UserServiceError.INVALID_PARAMETER, exception.getError());
    }

}