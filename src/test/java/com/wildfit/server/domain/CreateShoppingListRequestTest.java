package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CreateShoppingListRequestTest {

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(CreateShoppingListRequest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(CreateShoppingListRequest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(CreateShoppingListRequest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CreateShoppingListRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = CreateShoppingListRequest.builder()
                .withUuid("unique user id")
                .build();
        assertEquals("unique user id", request.getUuid());
        assertNull(request.getMealId());
    }
}