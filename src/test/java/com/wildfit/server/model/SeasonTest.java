package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.wildfit.server.domain.SeasonType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SeasonTest {
    @Test
    void tiedToSeasonType() {
        for (var enm : SeasonType.values()) {
            Season.valueOf(enm.name());
        }
        for (var enm : Season.values()) {
            assertNotNull(enm.toSeasonType());
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" Spring ", "apple"})
    void notFound(String code) {
        assertNull(Season.findByCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Spring", "Fall"})
    void findByCode(String code) {
        assertNotNull(Season.findByCode(code));
    }

    @Test
    void distinctCodes() {
        final var values = Season.values();
        final var codes = Arrays.stream(values).map(Season::getCode).collect(Collectors.toSet());
        assertEquals(values.length, codes.size());
    }
}