package com.wildfit.server.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class UserDigestTest {
    @Autowired
    private JacksonTester<UserDigest> json;

    @Test
    public void shouldHaveNoArgsConstructor() {
        assertThat(UserDigest.class, hasValidBeanConstructor());
    }

    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(UserDigest.class, hasValidGettersAndSetters());
    }

    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(UserDigest.class, hasValidBeanToString());
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UserDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var digest = UserDigest.builder()
                                     .withEmail("email")
                                     .build();

        assertEquals("email", digest.getEmail());
    }

    @Test
    public void testDeserialize() throws Exception {
        String jsonContent = "{\"userName\":\"   Mike Callas \",   \"password\":\"S1D2notS!\"," +
                " \"id\": \"    456     \" ," +
                " \"email\": \"         \" }";

        UserDigest result = this.json.parse(jsonContent).getObject();

        assertThat(result.getId()).isEqualTo(456);
        assertNull(result.getEmail());
    }
}