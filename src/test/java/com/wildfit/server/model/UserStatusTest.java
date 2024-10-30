package com.wildfit.server.model;

import com.wildfit.server.domain.UserStatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserStatusTest {

    @Test
    void toUserStatusType() {
        for (var enm : UserStatus.values()) {
            assertNotNull(enm.toUserStatusType());
        }
    }

    @Test
    void tiedToUserStatusType() {
        for (var enm : UserStatusType.values()) {
            assertNotNull(UserStatus.map(enm));
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