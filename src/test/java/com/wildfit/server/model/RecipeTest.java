package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecipeTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Recipe.class, hasValidBeanConstructor());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Recipe.class)
                      .withPrefabValues(RecipeGroup.class, new RecipeGroup().setId(3L), new RecipeGroup().setId(13L))
                      .suppress(Warning.NONFINAL_FIELDS)
                      .suppress(Warning.SURROGATE_KEY).verify();
    }
}