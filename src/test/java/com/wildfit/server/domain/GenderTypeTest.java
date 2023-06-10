package com.wildfit.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class GenderTypeTest {

    @Test
    void findByCode() {
        assertEquals("F", GenderType.FEMALE.getCode());
        assertEquals(GenderType.FEMALE, GenderType.findByCode("F"));
    }

    @Test
    void findByCode_null() {
        assertNull(GenderType.findByCode(null));
    }

    @Test
    void findByCode_notFound() {
        assertNull(GenderType.findByCode("Apple"));
    }

    @Test
    void getCodeAsCharacter() {
        assertEquals('N', GenderType.NON_BINARY.getCodeAsCharacter());
    }
}