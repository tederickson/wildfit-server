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

        assertEquals(MAIL, digest.getEmail());
        assertEquals(UserStatusType.FREE, digest.getStatus());
        assertEquals(12314L, digest.getId());
        assertEquals(user.getUuid(), digest.getUuid());
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
        assertNull(digest.getUuid());
    }
}