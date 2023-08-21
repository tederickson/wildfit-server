package com.wildfit.server.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MealSummaryTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(MealSummary.class, hasValidBeanConstructor());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(MealSummary.class)
                      .withPrefabValues(MealSummary.class, new MealSummary().setId(3L), new MealSummary().setId(13L))
                      .suppress(Warning.NONFINAL_FIELDS)
                      .suppress(Warning.SURROGATE_KEY).verify();
    }

    @Test
    public void testToString() {

        final var entity = new MealSummary()
                .setMeal(new Meal())
                .setId(3L)
                .setRecipeId(9987L)
                .setCooked(true)
                .setPlanDate(LocalDate.of(2023, 7, 22));
        assertEquals("MealSummary{id=3, recipeId=9987, cooked=true, planDate=2023-07-22}", entity.toString());
        assertNotNull(entity.getMeal());
    }
}