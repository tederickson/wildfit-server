package com.wildfit.server.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhotoDigestTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(PhotoDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builder() {
        final var request = PhotoDigest.builder().withThumb("p").build();
        assertEquals("p", request.thumb());
    }
}