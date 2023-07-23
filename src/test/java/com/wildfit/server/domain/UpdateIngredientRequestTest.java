package com.wildfit.server.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class UpdateIngredientRequestTest {

    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(UpdateIngredientRequest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(UpdateIngredientRequest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(UpdateIngredientRequest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UpdateIngredientRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var updateUserProfileRequest = UpdateIngredientRequest.builder()
                                                                    .withDescription("Really good stuff")
                                                                    .withIngredientServingQty(1.11f)
                                                                    .withIngredientServingUnit("banana")
                                                                    .withIngredientType(IngredientType.PRODUCE)
                                                                    .build();


        assertEquals("Really good stuff", updateUserProfileRequest.getDescription());
        assertEquals(IngredientType.PRODUCE, updateUserProfileRequest.getIngredientType());
    }
}