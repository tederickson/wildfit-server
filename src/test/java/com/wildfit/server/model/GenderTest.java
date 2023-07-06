package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.wildfit.server.domain.GenderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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
    void tiedToGenderType() {
        for (var enm : GenderType.values()) {
            Gender.valueOf(enm.name());
        }
        for (var enm : Gender.values()) {
            assertNotNull(enm.toGenderType());
        }
    }
}