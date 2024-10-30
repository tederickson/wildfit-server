package com.wildfit.server.domain;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfileDigestTest {
    final static UserDigest userDigest = UserDigest.builder()
            .withId(123L)
            .withEmail("email")
            .withStatus(UserStatusType.PREMIUM)
            .build();

    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(() -> userDigest, UserDigest.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(UserProfileDigest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(UserProfileDigest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(UserProfileDigest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UserProfileDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var userProfileDigest = UserProfileDigest.builder()
                .withUser(userDigest)
                .withAge(39)
                .withGender(GenderType.FEMALE)
                .withWeight(185.7f)
                .withHeightFeet(5)
                .withHeightInches(3)
                .build();

        assertEquals("email", userProfileDigest.getUser().getEmail());
        assertEquals(39, userProfileDigest.getAge());
    }
}