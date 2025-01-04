package com.wildfit.server.model;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

class UserProfileTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(() -> {
            final var user = new User();
            user.setId(1234L);

            return user;
        }, User.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(UserProfile.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(UserProfile.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(UserProfile.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UserProfile.class).suppress(Warning.SURROGATE_KEY).verify();
    }
}