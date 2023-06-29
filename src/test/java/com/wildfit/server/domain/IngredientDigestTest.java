package com.wildfit.server.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IngredientDigestTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(() -> PhotoDigest.builder().withThumb("thumb").build(), PhotoDigest.class);
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(IngredientDigest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(IngredientDigest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(IngredientDigest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(IngredientDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = IngredientDigest.builder().withFoodName("p").build();
        assertEquals("p", request.getFoodName());
    }
}