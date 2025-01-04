package com.wildfit.server.model;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MealTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(Meal.class, hasValidBeanConstructor());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Meal.class)
                .withPrefabValues(Meal.class, new Meal().setId(3L), new Meal().setId(13L))
                .suppress(Warning.SURROGATE_KEY).verify();
    }

    @Test
    public void testToString() {

        final var entity = new Meal()
                .setId(3L)
                .setUuid("this-uuid")
                .setStartDate(LocalDate.of(2023, 7, 22))
                .setEndDate(LocalDate.of(2023, 8, 1));
        assertEquals("Meal{id=3, uuid='this-uuid', startDate=2023-07-22, endDate=2023-08-01, recipes=null}",
                     entity.toString());
    }
}