package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParseRecipeRequestTest {
    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(ParseRecipeRequest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(ParseRecipeRequest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(ParseRecipeRequest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(ParseRecipeRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void addIngredient() {
        final var parseRecipeRequest = new ParseRecipeRequest();
        parseRecipeRequest.addIngredient(2.1f, "tsp", "coconut oil");
        assertEquals("2.1 tsp coconut oil", parseRecipeRequest.getQuery());
        assertTrue(parseRecipeRequest.isLine_delimited());

        parseRecipeRequest.addIngredient(1f, "cup", "cilantro");
        assertEquals("2.1 tsp coconut oil\n1.0 cup cilantro", parseRecipeRequest.getQuery());
    }

    @Test
    void addIngredient_missingServingUnit() {
        final var parseRecipeRequest = new ParseRecipeRequest();
        parseRecipeRequest.addIngredient(1f, null, "egg");
        assertEquals("1.0 egg", parseRecipeRequest.getQuery());
    }

    @Test
    void addIngredient_matchingServingUnit() {
        final var parseRecipeRequest = new ParseRecipeRequest();
        parseRecipeRequest.addIngredient(1f, "cantelope", "cantelope");
        assertEquals("1.0 cantelope", parseRecipeRequest.getQuery());
    }
}