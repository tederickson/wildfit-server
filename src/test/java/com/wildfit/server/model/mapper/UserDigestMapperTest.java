package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;

class UserDigestMapperTest {

    public static final String MAIL = "bob@bob.com";

    @Test
    void map() {
        final var user = User.builder()
                .withId(12314L)
                .withEmail(MAIL)
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(new Date())
                .withPassword("encodedPassword")
                .build();

        final var digest = UserDigestMapper.map(user);

        assertNull(digest.getPassword());
        assertEquals(MAIL, digest.getEmail());
    }

    @Test
    void map_null() {
        assertThrows(NullPointerException.class,
                () -> UserDigestMapper.map(null));
    }

    @Test
    void map_empty() {
        final var user = User.builder().build();

        final var digest = UserDigestMapper.map(user);

        assertNull(digest.getPassword());
        assertNull(digest.getEmail());
    }
}