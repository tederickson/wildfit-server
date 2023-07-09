package com.wildfit.server.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.UUID;

import com.wildfit.server.domain.UserStatusType;
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
                .withCreateDate(LocalDate.now())
                .withPassword("encodedPassword")
                .withUuid(UUID.randomUUID().toString())
                .build();

        final var digest = UserDigestMapper.map(user);

        assertEquals(MAIL, digest.getEmail());
        assertEquals(UserStatusType.FREE, digest.getStatus());
        assertEquals(12314L, digest.getId());
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

        assertNull(digest.getEmail());
        assertNull(digest.getStatus());
    }
}