package com.wildfit.server.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserProfileDigestTest {
    final static UserDigest userDigest = UserDigest.builder()
            .withPassword("password")
            .withEmail("email")
            .build();

    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(() -> userDigest, UserDigest.class);
    }

    @Test
    public void shouldHaveANoArgsConstructor() {
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
                .withHeight(65.23f)
                .build();

        assertEquals("password", userProfileDigest.getUser().getPassword());
        assertEquals(39, userProfileDigest.getAge());
    }
}