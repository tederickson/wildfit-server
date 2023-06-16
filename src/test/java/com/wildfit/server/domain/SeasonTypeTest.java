package com.wildfit.server.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SeasonTypeTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" Spring ", "apple"})
    void notFound(String code) {
        assertNull(SeasonType.findByCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Spring", "Fall"})
    void findByCode(String code) {
        assertNotNull(SeasonType.findByCode(code));
    }
}