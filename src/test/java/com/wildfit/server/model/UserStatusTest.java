package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.wildfit.server.domain.UserStatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserStatusTest {

    @Test
    void tiedToUserStatusType() {
        for (var enm : UserStatus.values()) {
            assertNotNull(enm.toUserStatusType());
        }
        for (var enm : UserStatusType.values()) {
            UserStatus.valueOf(enm.name());
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" Spring ", "apple"})
    void notFound(String code) {
        assertNull(UserStatus.findByCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"F", "P"})
    void findByCode(String code) {
        assertNotNull(UserStatus.findByCode(code));
    }

    @Test
    void distinctCodes() {
        final var values = UserStatus.values();
        final var codes = Arrays.stream(values).map(UserStatus::getCode).collect(Collectors.toSet());
        assertEquals(values.length, codes.size());
    }
}