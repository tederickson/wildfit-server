package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.code.beanmatchers.BeanMatchers;
import com.wildfit.server.util.ReadRecipeDigest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecipeGroupTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(RecipeGroup.class, hasValidBeanConstructor());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(RecipeGroup.class)
                      .withPrefabValues(RecipeGroup.class, new RecipeGroup().setId(3L), new RecipeGroup().setId(13L))
                      .suppress(Warning.NONFINAL_FIELDS)
                      .suppress(Warning.SURROGATE_KEY).verify();
    }

    @Test
    public void testToString() {
        final var recipe = ReadRecipeDigest.getRecipe("Tuna_salad.json");
        final var recipeGroup = recipe.getRecipeGroups().get(1);

        assertEquals(recipe, recipeGroup.getRecipe());
        assertEquals("RecipeGroup1{id=null, recipeGroupNumber=1, name='Dressing'}", recipeGroup.toString());
    }
}