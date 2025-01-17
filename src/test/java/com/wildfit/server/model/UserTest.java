package com.wildfit.server.model;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringExcluding;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSettersExcluding;
import static org.hamcrest.MatcherAssert.assertThat;

class UserTest {
    @BeforeAll
    static void setUp() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(User.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(User.class, hasValidGettersAndSettersExcluding("userStatus"));
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(User.class, hasValidBeanToStringExcluding("userStatus"));
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(User.class).suppress(Warning.SURROGATE_KEY).verify();
    }
}