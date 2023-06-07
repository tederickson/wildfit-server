package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wildfit.server.model.User;
import org.junit.jupiter.api.Test;

class UserDigestMapperTest {

    public static final String USER_NAME = "Bob";
    public static final String MAIL = "bob@bob.com";

    @Test
    void map() {
        final var user = User.builder()
                .withId(12314L)
                .withUserName(USER_NAME)
                .withPassword("encodedPassword")
                .withEmail(MAIL).build();
        final var mapper = new UserDigestMapper();

        final var digest = mapper.map(user);

        assertEquals(USER_NAME, digest.getUserName());
        assertNull(digest.getPassword());
        assertEquals(MAIL, digest.getEmail());
    }

    @Test
    void map_null() {
        final var mapper = new UserDigestMapper();
        assertThrows(NullPointerException.class,
                () -> mapper.map(null));
    }

    @Test
    void map_empty() {
        final var user = User.builder().build();
        final var mapper = new UserDigestMapper();

        final var digest = mapper.map(user);
        assertNull(digest.getUserName());
        assertNull(digest.getPassword());
        assertNull(digest.getEmail());
    }
}