package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateUserProfileRequestTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UpdateUserProfileRequest.class).verify();
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

        assertEquals("Bob Jones III", updateUserProfileRequest.name());
        assertEquals(39, updateUserProfileRequest.age());
    }
}