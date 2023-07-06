package com.wildfit.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.wildfit.server.domain.GenderType;
import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    void findByCode() {
        assertEquals("F", Gender.FEMALE.getCode());
        assertEquals(Gender.FEMALE, Gender.findByCode("F"));
        assertEquals(Gender.FEMALE, Gender.findByCode('F'));
    }

    @Test
    void findByCode_null() {
        assertNull(Gender.findByCode(null));
    }

    @Test
    void findByCode_notFound() {
        assertNull(Gender.findByCode("Apple"));
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