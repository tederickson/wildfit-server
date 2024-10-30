package com.wildfit.server.model;

import com.wildfit.server.domain.GenderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GenderTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" Spring ", "apple"})
    void notFound(String code) {
        assertNull(Gender.findByCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"F", "N"})
    void findByCode(String code) {
        assertNotNull(Gender.findByCode(code));
    }

    @Test
    void getCodeAsCharacter() {
        assertEquals('N', Gender.NON_BINARY.getCodeAsCharacter());
    }

    @Test
    void map() {
        for (var enm : GenderType.values()) {
            assertNotNull(Gender.map(enm));
        }
    }

    @Test
    void toGenderType() {
        for (var enm : Gender.values()) {
            assertNotNull(enm.toGenderType());
        }
    }

    @Test
    void distinctCodes() {
        final var values = Gender.values();
        final var codes = Arrays.stream(values).map(Gender::getCode).collect(Collectors.toSet());
        assertEquals(values.length, codes.size());
    }
}