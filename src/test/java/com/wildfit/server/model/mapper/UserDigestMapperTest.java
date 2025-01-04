package com.wildfit.server.model.mapper;

import com.wildfit.server.domain.UserStatusType;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertEquals(MAIL, digest.email());
        assertEquals(UserStatusType.FREE, digest.status());
        assertEquals(12314L, digest.id());
        assertEquals(user.getUuid(), digest.uuid());
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

        assertNull(digest.email());
        assertNull(digest.status());
        assertNull(digest.uuid());
    }
}