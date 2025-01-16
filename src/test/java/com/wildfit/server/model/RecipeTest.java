package com.wildfit.server.model;

import com.google.code.beanmatchers.BeanMatchers;
import com.wildfit.server.util.ReadRecipeDigest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    void testToString() {
        final var recipe = ReadRecipeDigest.getRecipe("Tuna_salad_with_apple_and_celery.json");

        assertNotNull(recipe);
        assertTrue(recipe.toString().contains("name='Tuna salad with apple and celery'"));
    }

    @Test
    void testInitializedRecipe() {
        final var recipe = new Recipe();

        assertTrue(recipe.toString().contains("recipeGroups size=0"));
        recipe.assignAllParents();
    }

    @Test
    void testAuditDates() {
        final var recipe = ReadRecipeDigest.getRecipe("Tuna_salad_with_apple_and_celery.json");

        assertNotNull(recipe);
        assertNull(recipe.getUpdated());
        assertEquals(LocalDate.now(), recipe.getCreated().toLocalDate());
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(Recipe.class, hasValidBeanConstructor());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(Recipe.class)
                .withPrefabValues(RecipeGroup.class, new RecipeGroup().setId(3L), new RecipeGroup().setId(13L))
                .suppress(Warning.SURROGATE_KEY).verify();
    }
}