package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JsonTest
class UserDigestTest {
    @Autowired
    private JacksonTester<UserDigest> json;

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UserDigest.class).verify();
    }

    @Test
    void builder() {
        final var digest = UserDigest.builder()
                .withEmail("email")
                .build();

        assertEquals("email", digest.email());
    }

    @Test
    public void testDeserialize() throws Exception {
        String jsonContent = "{\"userName\":\"   Mike Callas \",   \"password\":\"S1D2notS!\"," +
                " \"id\": \"    456     \" ," +
                " \"email\": \"         \" }";

        UserDigest result = this.json.parse(jsonContent).getObject();

        assertThat(result.id()).isEqualTo(456);
        assertFalse(result.email().isEmpty());
        assertTrue(result.email().isBlank());
    }
}