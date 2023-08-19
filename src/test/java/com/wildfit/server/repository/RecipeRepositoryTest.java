package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.wildfit.server.model.Season;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class RecipeRepositoryTest {
    static final Pageable PAGEABLE = PageRequest.of(0, 20);

    static final String EMAIL = "ted.erickson@comcast.net";


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
        final var name = "RecipeRepositoryTest Test Recipe";
        final var recipe = new com.wildfit.server.model.Recipe()
                .setSeason(Season.SPRING)
                .setEmail(EMAIL)
                .setName(name)
                .setCreated(java.time.LocalDateTime.now())
                .setIntroduction("introduction")
                .setPrepTimeMin(3)
                .setCookTimeMin(14)
                .setServingUnit("serving")
                .setServingQty(4);
        final var saved = recipeRepository.save(recipe);

        final var rows = recipeRepository.findAllBySeason(Season.SPRING, PAGEABLE);
        final var dbRecipe = rows.stream().filter(x -> Season.SPRING.equals(x.getSeason()))
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