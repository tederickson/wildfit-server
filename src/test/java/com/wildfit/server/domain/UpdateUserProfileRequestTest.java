package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateUserProfileRequestTest {
    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(UpdateUserProfileRequest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(UpdateUserProfileRequest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(UpdateUserProfileRequest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UpdateUserProfileRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var updateUserProfileRequest = UpdateUserProfileRequest.builder()
                .withName("Bob Jones III")
                .withAge(39)
                .withGender(GenderType.FEMALE)
                .withWeight(185.7f)
                .withHeightFeet(5)
                .withHeightInches(7)
                .build();

        assertEquals("Bob Jones III", updateUserProfileRequest.getName());
        assertEquals(39, updateUserProfileRequest.getAge());
    }
}