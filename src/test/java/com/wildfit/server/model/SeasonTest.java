package com.wildfit.server.model;

import com.wildfit.server.domain.SeasonType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SeasonTest {
    @Test
    void map() {
        for (var enm : SeasonType.values()) {
            assertNotNull(Season.map(enm));
        }
    }

    @Test
    void toSeasonType() {
        for (var enm : Season.values()) {
            assertNotNull(enm.toSeasonType());
        }
    }
}