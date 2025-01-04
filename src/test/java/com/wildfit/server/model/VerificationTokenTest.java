package com.wildfit.server.model;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class VerificationTokenTest {
    @BeforeAll
    public static void init() {
        BeanMatchers.registerValueGenerator(() -> {
            final var user = new User();
            user.setId(1234L);

            return user;
        }, User.class);
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        BeanMatchers.registerValueGenerator(LocalDateTime::now, LocalDateTime.class);
    }

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(VerificationToken.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(VerificationToken.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(VerificationToken.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(VerificationToken.class)
                
                .suppress(Warning.SURROGATE_KEY).verify();
    }

    @Test
    public void constructor() {
        final var token = "Token";
        final var user = new User();
        user.setId(1234L);

        final var verificationToken = new VerificationToken(token, user);
        assertEquals(token, verificationToken.getToken());
        assertEquals(user, verificationToken.getUser());
        assertNotNull(verificationToken.getExpiryDate());
        assertNull(verificationToken.getId());
    }
}