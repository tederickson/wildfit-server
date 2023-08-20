package com.wildfit.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.domain.SeasonType;
import com.wildfit.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {
    private static final Pageable PAGEABLE = PageRequest.of(1, 20);

    @Mock
    UserRepository userRepository;
    @Mock
    com.wildfit.server.repository.RecipeRepository recipeRepository;

    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(userRepository, recipeRepository);
    }

    @Test
    void listBySeason_nullSeasonType() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.listBySeason(null, PAGEABLE));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void listBySeason_nullPageable() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.listBySeason(SeasonType.FALL, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void listBySeasonAndIngredient() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.listBySeasonAndIngredient(SeasonType.SUMMER, null, PAGEABLE));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void listBySeasonAndName() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.listBySeasonAndName(SeasonType.SUMMER, null, PAGEABLE));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void retrieveRecipe() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.retrieveRecipe(null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void deleteRecipe() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.deleteRecipe(null, "userId"));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void createRecipe() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.createRecipe(null, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }

    @Test
    void updateRecipe() {
        final var exception = assertThrows(com.wildfit.server.exception.WildfitServiceException.class,
                () -> recipeService.updateRecipe(null, null));
        assertEquals("Invalid parameter.", exception.getMessage());
    }
}