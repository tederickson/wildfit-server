package com.wildfit.server.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
    Recipe1Repository recipe1Repository;

    @Test
    void findAllById() {
        final var noRows = recipe1Repository.findAllById(List.of(-123L));
        assertNotNull(noRows);
        assertFalse(noRows.iterator().hasNext());
    }

    @Test
    void findAllBySeason() {
        final var name = "RecipeRepositoryTest Test Recipe1";
        final var recipe = new com.wildfit.server.model.Recipe()
                .setSeasonName(SEASON)
                .setEmail(EMAIL)
                .setName(name)
                .setCreated(java.time.LocalDateTime.now())
                .setIntroduction("introduction")
                .setPrepTimeMin(3)
                .setCookTimeMin(14)
                .setServingUnit("serving")
                .setServingQty(4);
        final var saved = recipe1Repository.save(recipe);

        final var rows = recipe1Repository.findAllBySeasonName(SEASON, PAGEABLE);
        final var dbRecipe = rows.stream().filter(x -> SEASON.equals(x.getSeasonName()))
                                 .filter(x -> EMAIL.equals(x.getEmail()))
                                 .filter(x -> name.equals(x.getName()))
                                 .findFirst().orElse(null);

        assertNotNull(dbRecipe);

        final var searchById = recipe1Repository.findAllById(List.of(saved.getId()));
        final var iterator = searchById.iterator();

        assertTrue(iterator.hasNext());
        while (iterator.hasNext()) {
            assertEquals(saved.getId(), iterator.next().getId());
        }
        recipe1Repository.delete(saved);
    }
}