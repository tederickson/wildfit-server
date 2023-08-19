package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.code.beanmatchers.BeanMatchers;
import com.wildfit.server.domain.RecipeDigest;
import com.wildfit.server.model.mapper.RecipeMapper;
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
    public void shouldHaveANoArgsConstructor() {
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
    public void testToString() throws IOException {
        final var digest = getRecipeDigest("Tuna_salad.json");
        final var recipe = RecipeMapper.create(digest, "bob@bob.net");
        final var recipeGroup = recipe.getRecipeGroups().get(1);

        assertEquals(recipe, recipeGroup.getRecipe());
        assertEquals("RecipeGroup1{id=null, recipeGroupNumber=1, name='Dressing'}", recipeGroup.toString());
    }

    protected RecipeDigest getRecipeDigest(String fileName) throws IOException {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            final var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            return mapper.readValue(in, RecipeDigest.class);
        }
    }
}