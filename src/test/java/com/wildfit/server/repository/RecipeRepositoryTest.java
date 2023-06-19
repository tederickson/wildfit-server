package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.wildfit.server.model.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class RecipeRepositoryTest {
    static final Pageable PAGEABLE = PageRequest.of(0, 20);

    static final String EMAIL = "ted.erickson@comcast.net";
    static final String SEASON = "Spring";

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    void findAllById() {
        final var noRows = recipeRepository.findAllById(List.of(-123L));
        assertNotNull(noRows);
        assertFalse(noRows.iterator().hasNext());
    }

    @Test
    void findAllBySeason() {
        final var name = "Test Recipe";
        final var recipe = Recipe.builder()
                .withSeason(SEASON)
                .withEmail(EMAIL)
                .withName(name)
                .withCreated(java.time.LocalDateTime.now())
                .withInstructions("instructions")
                .withIntroduction("introduction")
                .withPrepTimeMin(3)
                .withCookTimeMin(14)
                .withServingUnit("serving")
                .withServingQty(4)
                .build();
        final var saved = recipeRepository.save(recipe);

        final var rows = recipeRepository.findAllBySeason(SEASON, PAGEABLE);
        final var dbRecipe = rows.stream().filter(x -> SEASON.equals(x.getSeason()))
                .filter(x -> EMAIL.equals(x.getEmail()))
                .filter(x -> name.equals(x.getName()))
                .findFirst().orElse(null);

        assertNotNull(dbRecipe);

        final var searchById = recipeRepository.findAllById(List.of(saved.getId()));
        final var iterator = searchById.iterator();

        assertTrue(iterator.hasNext());
        while (iterator.hasNext()) {
            assertEquals(saved.getId(), iterator.next().getId());
        }
        recipeRepository.delete(saved);
    }
}